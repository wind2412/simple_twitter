package zxl.bean;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class Article {
	
	private long time;			//发布时间
	private String content;		//推文的内容
	private String path;		//推文在服务器的路径
	private long UID;			//作者代号
	private long AID;			//文章编号
	private long trans_AID;		//用作转发的AID 即：此文章是一篇转发的文章，trans_AID是引用文章的AID.
	
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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

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
	
	public void add_article_to_disk() throws IOException {
		if(this.path != null && this.content != null) {
			File file = new File(this.path);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
			bw.write(this.content);
			bw.close();
		}
	}
	
}
