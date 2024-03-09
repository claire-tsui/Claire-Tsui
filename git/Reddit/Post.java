///////////////////////////////////////////////////////////////////////////////
// Title:              Post
// Files:              Post.java.class User.java.class Tester.java.class
// Quarter:            FALL 2022
//
// Author:             Claire Tsui
// Email:              ctsui@ucsd.edu
// Instructor's Name:  Professor Cao
import java.util.ArrayList;
/**
 * This class initializes and updates the title, author, upvote count, 
 * downvote count, etc of the post.
 * 
 * Bugs:N/A
 * 
 * @author Claire Tsui
 */
public class Post {
    private String title;
    private String content;
    private Post replyTo;
    private User author;
    private int upvoteCount;
    private int downvoteCount;

    /**
     * Initialize the title, content, and author 
     *
     * @param title title of the post
     * @param content content of the post
     * @param author author of the post
     */
    public Post(String title, String content, User author){
        this.title=title;
        this.content=content;
        this.author=author;
        upvoteCount=1;
        downvoteCount=0;
    }
    /**
     * Initialize the content, post this post is replying to, and author of 
     * the post.
     *
     * @param content content of the post
     * @param replyTo post this post is replying to 
     * @param author author of the post
     */
    public Post(String content, Post replyTo, User author){
        this.content=content;
        this.replyTo=replyTo;
        this.author=author;
        upvoteCount=1;
        downvoteCount=0;
    }
    /**
     * Get the title of the post
     * 
     * @return title of the post
     */
    public String getTitle(){
        return this.title;
    }
    /**
     * Get the post of this post is replying to
     * 
     * @return post this post replies to
     */
    public Post getReplyTo(){
        return this.replyTo;
    }
    /**
     * Get the author of the post
     * 
     * @return author of the post
     */
    public User getAuthor(){
        return this.author;
    }
    /**
     * Get the upvote count of this post
     * 
     * @return upvote count of this post
     */
    public int getUpvoteCount(){
        User name=getAuthor();
        this.author=name;
        return this.upvoteCount;
    }
    /**
     * Get the down count of this post
     * 
     * @return down count of this post
     */
    public int getDownvoteCount(){
        User name=getAuthor();
        this.author=name;
        return this.downvoteCount;
    }
    /**
     * Update the upvote count of this post
     * 
     * @param isIncrement determine whether to plus or minus the upvote 
     * count by 1
     */
    public void updateUpvoteCount(boolean isIncrement){
        if(isIncrement==true){
            this.upvoteCount++;
        }
        else{
            this.upvoteCount--;
        }
    }
    /**
     * Update the down count of this post
     * 
     * @param isIncrement determine whether to plus or minus the down 
     * count by 1
     */
    public void updateDownvoteCount(boolean isIncrement){
        if(isIncrement==true){
            this.downvoteCount++;
        }
        else{
            this.downvoteCount--;
        }
    }
    /**
     * Get the thread of this post and its comment(s)
     * 
     * @return list of this post and comment(s) 
     */
    public ArrayList<Post> getThread(){
        ArrayList<Post> postInThread = new ArrayList<Post>();
        ArrayList<Post> newPostInThread = new ArrayList<Post>();
        // this post is a post 
        if(this.getReplyTo()==null){
            postInThread.add(this);
            return postInThread;
        }
        else{
            Post reply=this.getReplyTo();
            while(reply!=null){
                postInThread.add(reply);
                reply=reply.getReplyTo();
            }
            // reverse the order to post, comment1, comment2...
            for (int i=postInThread.size()-1;i>=0;i--) {
                newPostInThread.add(postInThread.get(i));
            }
            // add current comment
            newPostInThread.add(this);
            return newPostInThread;
        }
    }
    /**
     * Convert posts or comments to string format
     * 
     * @return posts or comments in string format 
     */
    public String toString(){
        String TO_STRING_POST_FORMAT = "[%d|%d]\t%s\n\t%s";
        String TO_STRING_COMMENT_FORMAT = "[%d|%d]\t%s";
        String post=String.format(TO_STRING_POST_FORMAT,upvoteCount,downvoteCount,title,content);
        String comment=String.format(TO_STRING_COMMENT_FORMAT,upvoteCount,downvoteCount,content);
        if(title==null){
            return comment;
        }
        else{
            return post;
        }
    } 
}