package zxl.bean;


public class Comment {

	private long time;			//发布时间
	private String content;		//评论的内容
//	private String path;		//评论在服务器的路径	//obsoleted.
	private long UID;			//评论者代号
	private long CID;			//此评论编号
	private long AID;			//所评论的推文的编号
	private long commented_CID;	//若是评论某个评论，被此评论评论的评论的CID号。AID是一定要有的。毕竟本质上全是评论，都要依附于一个AID。但是评论的评论的话，就需要递归查找此commented_CID了。
								//读取某一个文章下的所有评论，对于那些嵌套的评论，要找到commented_CID为null为止才行。
	
	//评论被点赞的功能暂时不考虑
	public Comment(String content, long uID, long aID, long commented_CID) {
		super();
		this.content = content;
		UID = uID;
		AID = aID;
		this.commented_CID = commented_CID;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

//	public String getPath() {
//		return path;
//	}
//
//	public void setPath(String path) {
//		this.path = path;
//	}

	public long getUID() {
		return UID;
	}

	public void setUID(long uID) {
		UID = uID;
	}

	public long getCID() {
		return CID;
	}

	public void setCID(long cID) {
		CID = cID;
	}

	public long getAID() {
		return AID;
	}

	public void setAID(long aID) {
		AID = aID;
	}

	public long getCommented_CID() {
		return commented_CID;
	}

	public void setCommented_CID(long commented_CID) {
		this.commented_CID = commented_CID;
	}
	
	//鉴于与Article.add_article_to_disk()的理由，删除此函数即功能。
//	public void add_comment_to_disk() throws IOException {
//		if(this.path != null && this.content != null) {
//			File file = new File(this.path);
//			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
//			bw.write(this.content);
//			bw.close();
//		}
//	}
	
}
