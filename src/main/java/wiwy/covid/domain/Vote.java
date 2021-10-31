package wiwy.covid.domain;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.datetime.DateFormatter;
import wiwy.covid.domain.DTO.vote.VoteInputDTO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;

@Entity
@Getter
public class Vote {

    @Id @GeneratedValue
    private Long id;

    private String content;

    private int agree;
    private int disagree;

    @CreationTimestamp
    private Timestamp createTime;

    protected Vote() {

    }

    public Vote(VoteInputDTO voteInputDTO) {
        this.content = voteInputDTO.getContent();
    }

    public Vote modifyContent(String content) {
        this.content = content;
        return this;
    }

    public String makeDate() {
        return new SimpleDateFormat("yyyy년 MM월 dd일").format(this.createTime);
    }

    public void plusAgree() {
        this.agree += 1;
    }
    public void minusAgree() {
        this.agree -= 1;
    }
    public void plusDisagree() {
        this.disagree += 1;
    }
    public void minusDisagree() {
        this.disagree -= 1;
    }
}
