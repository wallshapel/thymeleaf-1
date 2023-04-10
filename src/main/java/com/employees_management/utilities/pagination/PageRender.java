package com.employees_management.utilities.pagination;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

public class PageRender<T> {
	private String url;
	private Page<T> page;
	private int totalPages;
	private int noElementsXPage;
	private int currentPage;
	private List<PageItem> pages;
	public PageRender(String url, Page<T> page) {
		this.url = url;
		this.page = page;
		this.pages = new ArrayList<PageItem>();
		noElementsXPage = 5;
		totalPages = page.getTotalPages();
		currentPage = page.getNumber() + 1;
		int from, until;
		if (totalPages <= noElementsXPage) {
			from = 1;
			until = totalPages;
		} else {
			if (currentPage <= noElementsXPage / 2) {
				from = 1;
				until = noElementsXPage;
			} else if (currentPage >= totalPages - noElementsXPage / 2) {
				from = totalPages - noElementsXPage + 1;
				until = noElementsXPage;
			} else {
				from = currentPage - noElementsXPage / 2;
				until = noElementsXPage;
			}
		}
		for (int i = 0; i < until; i++) {
			pages.add(new PageItem(from + i, currentPage == from + i));
		}
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currenPage) {
		this.currentPage = currenPage;
	}
	public List<PageItem> getPages() {
		return pages;
	}
	public void setPages(List<PageItem> pages) {
		this.pages = pages;
	}
	public boolean isFirst() {
		return page.isFirst();
	}
	public boolean isLast() {
		return page.isLast();
    }
	public boolean isHasNext() {
		return page.hasNext();
	}
	public boolean isHasPrevius() {
		return page.hasPrevious();
	}
}