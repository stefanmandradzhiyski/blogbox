package com.snmi.util;

import com.snmi.model.News;
import com.snmi.model.User;
import com.snmi.enums.NewsFilter;
import com.snmi.enums.Order;
import com.snmi.enums.UserFilter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * BlogFilter is a util class
 * Can be used to filter and order users or news lists by specific criteria
 * @author Stefan Mandradzhiyski
 * @version 1.0
 */
public class BlogFilter {

    private BlogFilter() {
        throw new IllegalArgumentException(BlogBoxGlobalConstant.UTIL_CLASS_EXCEPTION_MESSAGE);
    }

    public static List<User> filter(List<User> users, UserFilter userFilter, Order order) {
        List<User> filteredUsers;
        if (userFilter != null) {
            switch (userFilter) {
                case FIRST_NAME:
                    filteredUsers = users
                        .stream()
                        .sorted(Comparator.comparing(User::getFirstName))
                        .collect(Collectors.toList());
                    break;
                case USERNAME:
                    filteredUsers = users
                        .stream()
                        .sorted(Comparator.comparing(User::getUsername))
                        .collect(Collectors.toList());
                    break;
                case REGISTERED:
                    filteredUsers = users
                        .stream()
                        .sorted(Comparator.comparing(User::getCreatedAt))
                        .collect(Collectors.toList());
                    break;
                default:
                    filteredUsers = users;
                    break;
            }

            if (order == Order.DESC) {
                Collections.reverse(filteredUsers);
            }

            return filteredUsers;
        }

        return users;
    }

    public static List<News> filter(List<News> news, NewsFilter newsFilter, Order order) {
        List<News> filteredNews;
        if (newsFilter != null) {
            switch (newsFilter) {
                case NAME:
                    filteredNews = news
                        .stream()
                        .sorted(Comparator.comparing(News::getName))
                        .collect(Collectors.toList());
                    break;
                case VIEWS:
                    filteredNews = news
                        .stream()
                        .sorted(Comparator.comparing(News::getViewsCount))
                        .collect(Collectors.toList());
                    break;
                case LIKES:
                    filteredNews = news
                        .stream()
                        .sorted(Comparator.comparing(News::getLikesCount))
                        .collect(Collectors.toList());
                    break;
                case CREATED:
                    filteredNews = news
                        .stream()
                        .sorted(Comparator.comparing(News::getCreatedAt))
                        .collect(Collectors.toList());
                    break;
                default:
                    filteredNews = news;
                    break;
            }

            if (order == Order.DESC) {
                Collections.reverse(filteredNews);
            }

            return filteredNews;
        }

        return news;
    }

}
