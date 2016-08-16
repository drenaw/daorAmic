package com.example.root.roadamic.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by root on 15/8/16.
 */
public class ApiResult {

    @SerializedName("html_attributions")
    private List<String> html_attributions;

    @SerializedName("next_page_token")
    private String next_page_token;

    @SerializedName("results")
    private List<Garage> results;

    @SerializedName("status")
    private String status;

    public ApiResult(List<String> html_attributions, String next_page_token, List<Garage> results, String status) {
        this.html_attributions = html_attributions;
        this.next_page_token = next_page_token;
        this.results = results;
        this.status = status;
    }

    public List<String> getHtml_attributions() {
        return html_attributions;
    }

    public void setHtml_attributions(List<String> html_attributions) {
        this.html_attributions = html_attributions;
    }

    public String getNext_page_token() {
        return next_page_token;
    }

    public void setNext_page_token(String next_page_token) {
        this.next_page_token = next_page_token;
    }

    public List<Garage> getResults() {
        return results;
    }

    public void setResults(List<Garage> results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
