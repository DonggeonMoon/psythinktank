package com.dgmoonlabs.psythinktank.global.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ViewName {
    ROOT(""),
    CONTENT_PREFIX("contents/"),
    ARTICLE_LIST("articleList"),
    ARTICLE("ARTICLE"),
    VIEW_ARTICLE("viewArticle"),
    INSERT_ARTICLE("insertArticle"),
    UPDATE_ARTICLE("updateArticle"),
    NEWSLETTER_LIST("newsletterList"),
    INSERT_NEWSLETTER("insertNewsletter"),
    LOGIN("login"),
    MANAGER_PAGE("managerPage"),
    JOIN("join"),
    FIND_ID_AND_PASSWORD("findIdAndPw"),
    EDIT_MEMBER_INFO("editMemberInfo"),
    GOOD_BYE("goodBye"),
    STOCK_LIST("stockList"),
    STOCK("viewStock"),
    CHAT("chat"),
    SITEMAP_MANAGEMENT("sitemapManagement");

    private static final String REDIRECTION_PREFIX = "redirect:/";
    private final String text;

    public String redirect() {
        return REDIRECTION_PREFIX + this.text;
    }
}
