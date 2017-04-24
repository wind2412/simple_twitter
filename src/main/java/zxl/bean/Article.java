package zxl.bean;


public class Article {
	
	private long time;			//发布时间
	private String content;		//推文的内容
//	private String path;		//推文在服务器的路径	//obsoleted.
	private long UID;			//作者代号
	private long AID;			//文章编号
	private long trans_AID;		//用作转发的AID 即：此文章是一篇转发的文章，trans_AID是引用文章的AID.
//	private long transed_cnt;	//被转发次数		=>	   舍弃。请去get_transed:AID表去查找。		//obsoleted.
//	private long commented_cnt;	//被评论次数		=>     舍弃。请去get_commented:AID表去查找。	//obsoleted.
	
	public Article(String content, long UID, long trans_AID) {
		super();
		this.content = content;
		this.UID = UID;
		this.trans_AID = trans_AID;
	}

	public long getAID() {
		return AID;
	}

	public void setAID(long AID) {
		this.AID = AID;
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

	public void setUID(long UID) {
		this.UID = UID;
	}

	public long getTrans_AID() {
		return trans_AID;
	}

	public void setTrans_AID(long trans_AID) {
		this.trans_AID = trans_AID;
	}
	
	//由于网页一定会获取到评论和文章。如果再和磁盘进行交互，1.程序过于麻烦，2.还影响速度。而且，不如直接从redis内存中去取来得快啊。3.如果搜索引擎成功，直接通过内存库查找，还不是比磁盘更快吗。
//	public void add_article_to_disk() throws IOException {
//		if(this.path != null && this.content != null) {
//			File file = new File(this.path);
//			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
//			bw.write(this.content);
//			bw.close();
//		}
//	}
	
}
