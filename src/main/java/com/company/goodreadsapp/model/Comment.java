package com.company.goodreadsapp.model;


import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment extends BaseModel{
    private Long id;
    private Comment parent;
    private User user;
    private Book book;
    private String comment;
    private CommentStatus status;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Comment{");
        sb.append("id=").append(id);
        sb.append(", parent=").append(parent == null ? null : parent.toString());
        sb.append(", user=").append(user);
        sb.append(", book=").append(book);
        sb.append(", comment='").append(comment).append('\'');
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }
}
