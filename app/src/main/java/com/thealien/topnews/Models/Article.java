package com.thealien.topnews.Models;



public class Article {

    private String newsImageUrl;
    private String newsHeadLine;
    private String newsDescription;
    private String newsAuthor;
    private String fullArticleUrl;
    private String newsTime;

    /*public Article(String newsImageUrl, String newsHeadLine) {
        this.newsImageUrl = newsImageUrl;
        this.newsHeadLine = newsHeadLine;
    }*/

    public Article(String newsTime,String newsImageUrl, String newsHeadLine, String newsDescription, String newsAuthor, String fullArticleUrl) {
        this.newsTime = newsTime;
        this.newsImageUrl = newsImageUrl;
        this.newsHeadLine = newsHeadLine;
        this.newsDescription = newsDescription;
        this.newsAuthor = newsAuthor;
        this.fullArticleUrl = fullArticleUrl;
    }

    public String getNewsTime() {
        return newsTime;
    }

    public void setNewsTime(String newsTime) {
        this.newsTime = newsTime;
    }

    public String getNewsImageUrl() {
        return newsImageUrl;
    }

    public String getNewsHeadLine() {
        return newsHeadLine;
    }


    public String getNewsDescription() {
        if(newsDescription == "null"){
            return "No Description Found ";
        }
        return newsDescription;
    }

    public String getNewsAuthor() {
        if(newsAuthor == "null"){
            return "Unknown Author";
        }
        return newsAuthor;
    }

    public String getFullArticleUrl() {
        return fullArticleUrl;
    }
}