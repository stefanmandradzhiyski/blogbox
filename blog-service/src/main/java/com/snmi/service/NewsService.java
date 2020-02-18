package com.snmi.service;

import com.snmi.dto.NewsDTO;
import com.snmi.dto.NewsSearchDTO;
import com.snmi.dto.ShowStatisticDTO;
import com.snmi.dto.NewsStatisticDTO;
import com.snmi.dto.NewsUpdateStatusDTO;
import com.snmi.enums.Statistic;
import java.util.List;

/**
 * News service functionality contract
 * @author Stefan Mandradzhiyski
 * @version 1.0
 */
public interface NewsService {

    NewsDTO createNews(NewsDTO newsDTO);
    NewsDTO getNewsById(Long newsId, boolean display);
    List<NewsDTO> getAllNewsByUser(Long userId, boolean display);
    List<NewsDTO> getAll(boolean display);
    List<NewsDTO> searchNews(NewsSearchDTO newsSearchDTO, boolean display);
    List<ShowStatisticDTO> getStatistic(Long newsId, Statistic statistic);
    NewsDTO updateNews(NewsDTO newsDTO, boolean display);
    NewsDTO updateNewsStatistics(NewsStatisticDTO newsStatisticDTO);
    NewsDTO updateNewsStatus(NewsUpdateStatusDTO newsUpdateStatusDTO);
    NewsDTO deleteNews(Long newsId);

}
