package wiwy.covid.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Getter @Setter
@ToString
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String username;
    private String password;
    private String email;

    private String roles; // ROLE_USER, ROLE_ADMIN

    @CreationTimestamp
    private Timestamp createDate;

    private List<Long> likesPost = new ArrayList<>();
    private List<Long> hatesPost = new ArrayList<>();

    // 사용자가 해당 게시글에 좋아요를 눌렀는지 체크하는 메소드
    public boolean isLikePost(Long postId) {
        if (this.likesPost.contains(postId)) {
            return true;
        } else {
            return false;
        }
    }

    public void addLikePost(Long postId) {
        this.likesPost.add(postId);
    }
    public void removeLikePost(Long postId) {
         this.likesPost.remove(postId);
    }

    // 사용자가 해당 게시글에 싫어요를 눌렀는지 체크하는 메소드
    public boolean isHatePost(Long postId) {
        if (this.hatesPost.contains(postId)) {
            return true;
        } else {
            return false;
        }
    }

    public void addHatePost(Long postId) {
        this.hatesPost.add(postId);
    }
    public void removeHatePost(Long postId) {
        this.hatesPost.remove(postId);
    }

    public List<String> getRoleList() {
        if (this.roles.length() > 0) {
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }


    // 빌더를 만들었기 때문에 기본 생성자가 있어야 함. 접근제한을 위해 protected 사용
    protected Member() {

    }

    @Builder
    public Member(String username, String password, String email, String roles, Timestamp createDate) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.roles = roles;
        this.createDate = createDate;
    }
}
