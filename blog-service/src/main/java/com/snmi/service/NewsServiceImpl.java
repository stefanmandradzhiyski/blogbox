package com.snmi.service;

import com.snmi.dto.NewsDTO;
import com.snmi.dto.NewsSearchDTO;
import com.snmi.dto.NewsStatisticDTO;
import com.snmi.dto.NewsUpdateStatusDTO;
import com.snmi.dto.ShowStatisticDTO;
import com.snmi.enums.DetailLevel;
import com.snmi.enums.NewsFilter;
import com.snmi.enums.Order;
import com.snmi.enums.Statistic;
import com.snmi.enums.StatisticAction;
import com.snmi.enums.Status;
import com.snmi.exception.BlogBoxNotFoundException;
import com.snmi.model.Dislike;
import com.snmi.model.Favourite;
import com.snmi.model.Like;
import com.snmi.model.News;
import com.snmi.model.User;
import com.snmi.model.BlogBoxBaseStatistic;
import com.snmi.repository.BlogBoxBaseStatisticRepository;
import com.snmi.repository.CommentRepository;
import com.snmi.repository.NewsRepository;
import com.snmi.repository.NewsRepositoryImpl;
import com.snmi.repository.UserRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.snmi.util.BlogBoxGlobalConstant;
import com.snmi.util.BlogBoxSecurityUtil;
import com.snmi.util.BlogFilter;
import com.snmi.util.BlogMapper;
import com.snmi.util.BlogValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * News service implementation
 * @author Stefan Mandradzhiyski
 * @version 1.0
 */
@Service
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final NewsRepositoryImpl newsRepositoryImpl;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final BlogBoxBaseStatisticRepository<Like> likeRepository;
    private final BlogBoxBaseStatisticRepository<Dislike> dislikeRepository;
    private final BlogBoxBaseStatisticRepository<Favourite> favouriteRepository;
    private final BlogMapper blogMapper;

    public NewsServiceImpl(
        NewsRepository newsRepository,
        NewsRepositoryImpl newsRepositoryImpl,
        UserRepository userRepository,
        CommentRepository commentRepository,
        BlogBoxBaseStatisticRepository<Like> likeRepository,
        BlogBoxBaseStatisticRepository<Dislike> dislikeRepository,
        BlogBoxBaseStatisticRepository<Favourite> favouriteRepository,
        BlogMapper blogMapper
    ) {
        this.newsRepository = newsRepository;
        this.newsRepositoryImpl = newsRepositoryImpl;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.likeRepository = likeRepository;
        this.dislikeRepository = dislikeRepository;
        this.favouriteRepository = favouriteRepository;
        this.blogMapper = blogMapper;
    }

    /**
     * Create new news
     * @param newsDTO take the news data transfer object
     * @return the created news
     */
    @Override
    public NewsDTO createNews(NewsDTO newsDTO) {
        User user = findActiveUserByHisUsername();
        News news = blogMapper.toNews(newsDTO, user);

        News savedNews = newsRepository.save(news);

        return getNewsByIdWithoutComments(savedNews.getId(), true);
    }

    /**
     * Get specific news by id
     * @param newsId take the news id
     * @param display take the display flag
     * @return the found news
     */
    @Transactional
    @Override
    public NewsDTO getNewsById(Long newsId, boolean display) {
        DetailLevel detailLevel = BlogBoxSecurityUtil.getDetailLevelByAuthority();
        News news = findNewsById(newsId, display);
        news.setComments(commentRepository.findAllByNewsId(news.getId()));

        return blogMapper.toNewsDTO(news, detailLevel, true);
    }

    /**
     * Get news list created by specific user
     * If news list belongs to the requester he will see all of them regardless of status
     * If news list doesn't belong to the requester he will see only news with status APPROVED
     * @param userId take the user id
     * @param display take the display flag
     * @return the news list
     */
    @Override
    public List<NewsDTO> getAllNewsByUser(Long userId, boolean display) {
        BlogValidator.validateId(userId, User.class);

        List<News> userNews = newsRepository.findAllByUserIdAndDisplay(userId, display);
        if (!userNews.isEmpty()) {
            if (!BlogBoxSecurityUtil.isYours(userNews.get(0).getUser().getUsername()) &&
                    !BlogBoxSecurityUtil.haveYouGotAuthority(BlogBoxGlobalConstant.ROLE_ADMIN)) {
                userNews = newsRepository.findAllByUserIdAndDisplayAndStatus(userId, display, Status.APPROVED);
            }
        }

        return userNews
            .stream()
            .map(news -> blogMapper.toNewsDTO(news, DetailLevel.SHORT, false))
            .collect(Collectors.toList());
    }

    /**
     * Get all system news
     * @param display take the display flag
     * @return the news list
     */
    @Override
    public List<NewsDTO> getAll(boolean display) {
        return newsRepository.findAllByDisplay(display)
            .stream()
            .map(news -> blogMapper.toNewsDTO(news, DetailLevel.SHORT, false))
            .collect(Collectors.toList());
    }

    /**
     * Search for news by specific criteria
     * @param newsSearchDTO take the news search request data transfer object
     * @param display take the display flag
     * @return the filtered news list
     */
    @Transactional
    @Override
    public List<NewsDTO> searchNews(NewsSearchDTO newsSearchDTO, boolean display) {
        NewsFilter newsFilter = newsSearchDTO.getNewsFilter();
        Order newsOrder = newsSearchDTO.getOrder();
        Status status = defineSearchStatus(newsSearchDTO.getStatus());

        List<News> newsList = newsRepositoryImpl.searchNews(newsSearchDTO, status, display);

        return BlogFilter.filter(newsList, newsFilter, newsOrder)
                .stream()
                .map(news -> blogMapper.toNewsDTO(news, DetailLevel.SHORT, false))
                .collect(Collectors.toList());
    }

    /**
     * Get specific statistic for specific news
     * @param newsId take the news id
     * @param statistic take the desired statistic type
     * @return the statistic list
     */
    @Override
    public List<ShowStatisticDTO> getStatistic(Long newsId, Statistic statistic) {
        List<? extends BlogBoxBaseStatistic> blogBoxBaseStatistics = new ArrayList<>();
        News news = findNewsById(newsId, true);

        switch (statistic) {
            case LIKES:
                blogBoxBaseStatistics = likeRepository.findAllByNewsId(news.getId());
                break;
            case DISLIKES:
                blogBoxBaseStatistics = dislikeRepository.findAllByNewsId(news.getId());
                break;
            case FAVOURITES:
                blogBoxBaseStatistics = favouriteRepository.findAllByNewsId(news.getId());
                break;
            default:
                break;
        }

        return blogBoxBaseStatistics
            .stream()
            .map(blogMapper::toShowStatisticDTO)
            .collect(Collectors.toList());
    }

    /**
     * Update specific news
     * Writer can update only his news
     * @param newsDTO take the news data transfer object
     * @param display take the display flag
     * @return the updated news
     */
    @Transactional
    @Override
    public NewsDTO updateNews(NewsDTO newsDTO, boolean display) {
        News existingNews = findNewsById(newsDTO.getId(), display);
        BlogBoxSecurityUtil.controlYourData(existingNews.getUser().getUsername());
        News updatedNews = updateNews(existingNews, newsDTO);

        newsRepository.save(updatedNews);

        return getNewsByIdWithoutComments(updatedNews.getId(), display);
    }

    /**
     * Update news statistic
     * @param newsStatisticDTO take the news statistic data transfer object
     * @return the updated news
     */
    @Transactional
    @Override
    public NewsDTO updateNewsStatistics(NewsStatisticDTO newsStatisticDTO) {
        News existingNews = findNewsById(newsStatisticDTO.getNewsId(), true);
        User existingUser = findActiveUserByHisUsername();
        StatisticAction statisticAction = newsStatisticDTO.getStatisticAction();

        Long newStatisticCount;
        switch (newsStatisticDTO.getStatistic()) {
            case VIEWS:
                newStatisticCount = existingNews.getViewsCount();
                existingNews.setViewsCount(newStatisticCount + 1L);
                break;
            case LIKES:
                newStatisticCount = controlStatistic(
                        likeRepository,
                        new Like(),
                        existingNews,
                        existingUser,
                        statisticAction,
                        existingNews.getLikesCount()
                );
                existingNews.setLikesCount(newStatisticCount);
                break;
            case DISLIKES:
                newStatisticCount = controlStatistic(
                        dislikeRepository,
                        new Dislike(),
                        existingNews,
                        existingUser,
                        statisticAction,
                        existingNews.getDislikesCount()
                );
                existingNews.setDislikesCount(newStatisticCount);
                break;
            case FAVOURITES:
                newStatisticCount = controlStatistic(
                        favouriteRepository,
                        new Favourite(),
                        existingNews,
                        existingUser,
                        statisticAction,
                        existingNews.getFavouritesCount()
                );
                existingNews.setFavouritesCount(newStatisticCount);

                Long userFavouriteCount = favouriteRepository.countByUserId(existingUser.getId());
                existingUser.setFavouritesCount(userFavouriteCount);

                userRepository.save(existingUser);
                break;
            default: break;
        }

        newsRepository.save(existingNews);

        return getNewsByIdWithoutComments(existingNews.getId(), true);
    }

    /**
     * Update news status
     * @param newsUpdateStatusDTO take the news update status data transfer object
     * @return the updated news
     */
    @Transactional
    @Override
    public NewsDTO updateNewsStatus(NewsUpdateStatusDTO newsUpdateStatusDTO) {
        News foundNews = findNewsById(newsUpdateStatusDTO.getNewsId(), true);
        foundNews.setStatus(newsUpdateStatusDTO.getStatus());
        foundNews.setUpdatedAt(new Date());

        newsRepository.save(foundNews);

        return getNewsById(foundNews.getId(), true);
    }

    /**
     * Delete specific news
     * Writer can delete only his news
     * @param newsId take the news id
     * @return the news before the delete
     */
    @Transactional
    @Override
    public NewsDTO deleteNews(Long newsId) {
        News news = findNewsById(newsId, true);
        BlogBoxSecurityUtil.controlYourData(news.getUser().getUsername());
        news.setDisplay(false);
        news.setUpdatedAt(new Date());
        likeRepository.deleteAllByNewsId(news.getId());
        dislikeRepository.deleteAllByNewsId(news.getId());
        commentRepository.deleteAllByNewsId(news.getId());

        List<Favourite> favourites = favouriteRepository.findAllByNewsId(news.getId());
        favourites.forEach(favourite -> {
            User assignedUser = favourite.getUser();
            Long decreaseUserFavouritesCount = assignedUser.getFavouritesCount() - 1;
            assignedUser.setFavouritesCount(decreaseUserFavouritesCount);
            userRepository.save(assignedUser);
        });
        favouriteRepository.deleteAllByNewsId(news.getId());

        newsRepository.save(news);

        return blogMapper.toNewsDTO(news, DetailLevel.SHORT, false);
    }

    private NewsDTO getNewsByIdWithoutComments(Long newsId, boolean display) {
        DetailLevel detailLevel = BlogBoxSecurityUtil.getDetailLevelByAuthority();
        News news = findNewsById(newsId, display);

        return blogMapper.toNewsDTO(news, detailLevel, false);
    }

    private News findNewsById(Long newsId, boolean display) {
        BlogValidator.validateId(newsId, News.class);

        News news = newsRepository.findByIdAndDisplay(newsId, display)
                .orElseThrow(() -> new BlogBoxNotFoundException(News.class, newsId));

        if (BlogBoxSecurityUtil.isYours(news.getUser().getUsername())
                || BlogBoxSecurityUtil.haveYouGotAuthority(BlogBoxGlobalConstant.ROLE_ADMIN)) {
            return news;
        } else {
            return newsRepository.findByIdAndDisplayAndStatus(newsId, display, Status.APPROVED)
                    .orElseThrow(() -> new BlogBoxNotFoundException(News.class, newsId));
        }
    }

    private User findActiveUserByHisUsername() {
        String username = BlogBoxSecurityUtil.getLoggedInUserUsername();

        return userRepository.findByUsernameAndDisplay(username, true)
                .orElseThrow(() -> new BlogBoxNotFoundException(User.class, BlogBoxGlobalConstant.USERNAME, username));
    }

    private Status defineSearchStatus(Status requestStatus) {
        Status status;
        if (requestStatus != null
                && BlogBoxSecurityUtil.haveYouGotAuthority(BlogBoxGlobalConstant.ROLE_ADMIN)) {
            status = requestStatus;
        } else {
            status = Status.APPROVED;
        }

        return status;
    }

    private News updateNews(News existingNews, NewsDTO newsDTO) {
        existingNews.setName(newsDTO.getName());
        existingNews.setShortDescription(newsDTO.getShortDescription());
        existingNews.setMainInformation(newsDTO.getMainInformation());
        existingNews.setUpdatedAt(new Date());
        existingNews.setStatus(Status.WAITING);

        return existingNews;
    }

    private <T extends BlogBoxBaseStatisticRepository> Long controlStatistic(
        T blogBoxBaseStatisticRepository,
        BlogBoxBaseStatistic blogBoxBaseStatistic,
        News existingNews,
        User existingUser,
        StatisticAction statisticAction,
        Long currentStatisticCount
    ) {
        Long newsId = existingNews.getId();
        Long userId = existingUser.getId();
        boolean existingStatistic = blogBoxBaseStatisticRepository.existsByNewsIdAndUserId(newsId, userId);

        if (StatisticAction.INCREASE == statisticAction && !existingStatistic) {
            currentStatisticCount += 1;

            blogBoxBaseStatisticRepository.save(createStatistic(
                blogBoxBaseStatistic,
                existingNews,
                existingUser));
        } else if (StatisticAction.DECREASE == statisticAction && existingStatistic) {
            currentStatisticCount -= 1;

            blogBoxBaseStatisticRepository.deleteByNewsIdAndUserId(newsId, userId);
        }

        return currentStatisticCount;
    }

    private BlogBoxBaseStatistic createStatistic(
            BlogBoxBaseStatistic blogBoxBaseStatistic,
            News news,
            User user
    ) {
        blogBoxBaseStatistic.setNews(news);
        blogBoxBaseStatistic.setUser(user);
        return blogBoxBaseStatistic;
    }
}
