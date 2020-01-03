package com.zjh.blog.po;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="t_comment")
public class Comment {

	@Id
	@GeneratedValue
	private Long id;
	
	private String nickname;
	
	private String email;
	
	private String cotent;
	
	private String avatar;
	
	@ManyToOne
	private Blog blog;
	
	@OneToMany(mappedBy = "parent")
	private List<Comment> replaies = new ArrayList<>();
	
	@ManyToOne
	private Comment parent;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	
	private boolean adminComment;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCotent() {
		return cotent;
	}

	public void setCotent(String cotent) {
		this.cotent = cotent;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Blog getBlog() {
		return blog;
	}

	public void setBlog(Blog blog) {
		this.blog = blog;
	}

	public List<Comment> getReplaies() {
		return replaies;
	}

	public void setReplaies(List<Comment> replaies) {
		this.replaies = replaies;
	}

	public Comment getParent() {
		return parent;
	}

	public void setParent(Comment parent) {
		this.parent = parent;
	}

	public boolean isAdminComment() {
		return adminComment;
	}

	public void setAdminComment(boolean adminComment) {
		this.adminComment = adminComment;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", nickname=" + nickname + ", email=" + email + ", cotent=" + cotent + ", avatar="
				+ avatar + ", blog=" + blog + ", replaies=" + replaies + ", parent=" + parent + ", createTime="
				+ createTime + ", adminComment=" + adminComment + "]";
	}
}
