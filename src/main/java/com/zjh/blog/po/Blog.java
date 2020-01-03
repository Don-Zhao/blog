package com.zjh.blog.po;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name="t_blog")
public class Blog {

	@Id
	@GeneratedValue
	private Long id;
	
	private String title;
	
//	@Basic(fetch = FetchType.LAZY)
//	@Lob
	private String content;
	
	private String picture;
	
	private String flag;
	
	private Integer views;
	
	private boolean appreciation;
	
	private boolean shareStatement;
	
	private boolean commentEnabled;
	
	private boolean pushlished;
	
	private boolean recommend;
	
	private String description;
	
	@ManyToOne(cascade = {CascadeType.PERSIST})
	private Type type;
	
	@ManyToMany
	private List<Tag> tags = new ArrayList<>();
	
	@ManyToOne
	private User user;
	
	@OneToMany(mappedBy = "blog")
	private List<Comment> comments = new ArrayList<>();
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;
	
	@Transient
	private String tagIds;
	
	@Transient
	private String typeId;
	
	public Long getId() {
		return id;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Integer getViews() {
		return views;
	}

	public void setViews(Integer views) {
		this.views = views;
	}

	public boolean isAppreciation() {
		return appreciation;
	}

	public void setAppreciation(boolean appreciation) {
		this.appreciation = appreciation;
	}

	public boolean isShareStatement() {
		return shareStatement;
	}

	public void setShareStatement(boolean shareStatement) {
		this.shareStatement = shareStatement;
	}

	public boolean isCommentEnabled() {
		return commentEnabled;
	}

	public void setCommentEnabled(boolean commentEnabled) {
		this.commentEnabled = commentEnabled;
	}

	public boolean isPushlished() {
		return pushlished;
	}

	public void setPushlished(boolean pushlished) {
		this.pushlished = pushlished;
	}

	public boolean isRecommend() {
		return recommend;
	}

	public void setRecommend(boolean recommend) {
		this.recommend = recommend;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getTagIds() {
		return tagIds;
	}

	public void setTagIds(String tagIds) {
		this.tagIds = tagIds;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Blog [id=" + id + ", title=" + title + ", content=" + content + ", picture=" + picture + ", flag="
				+ flag + ", views=" + views + ", appreciation=" + appreciation + ", shareStatement=" + shareStatement
				+ ", commentEnabled=" + commentEnabled + ", pushlished=" + pushlished + ", recommend=" + recommend
				+ ", description=" + description + ", type=" + type + ", tags=" + tags + ", user=" + user
				+ ", comments=" + comments + ", createTime=" + createTime + ", updateTime=" + updateTime + ", tagIds="
				+ tagIds + ", typeId=" + typeId + "]";
	}
}
