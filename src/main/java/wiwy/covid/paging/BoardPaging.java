package wiwy.covid.paging;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter @Setter
public class BoardPaging {


    private int displayPageNum = 10;
    private int totalCount;

    private int startPage;
    private int endPage;
    private boolean prev;
    private boolean next;

    private Paging paging;

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;

        pagingData();
    }

    private void pagingData() {

        endPage = (int) (Math.ceil(paging.getPage()/(double)displayPageNum) * displayPageNum);
        startPage = (endPage - displayPageNum) + 1;

        int tempEndPage = (int) (Math.ceil(totalCount/(double)paging.getPerPageNum()));
        if(endPage > tempEndPage) {
            endPage = tempEndPage;
        }

        prev = startPage == 1 ? false : true;
        next = endPage * paging.getPerPageNum() >= totalCount ? false : true;

    }

    public boolean isPrev() {
        return prev;
    }

    public boolean isNext() {
        return next;
    }

}
