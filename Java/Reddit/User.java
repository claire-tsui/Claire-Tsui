///////////////////////////////////////////////////////////////////////////////
// Title:              User
// Files:              Post.java.class User.java.class Tester.java.class
// Quarter:            FALL 2022
//
// Author:             Claire Tsui
// Email:              ctsui@ucsd.edu
// Instructor's Name:  Professor Cao
import java.util.ArrayList;
/**
 * This class initializes and updates the karma, list of posts, list of upvoted 
 * post(s)/downvoted post(s), top post, etc of the user.
 * 
 * Bugs:N/A
 * 
 * @author Claire Tsui
 */
public class User {
    private int karma;
    private String username;
    private ArrayList<Post> posts;
    private ArrayList<Post> upvoted;
    private ArrayList<Post> downvoted;

    /**
     * Initialize the user's name
     * 
     * @param username name of the user
     */
    public User(String username){        
        this.username=username;
        this.posts=new ArrayList<Post>();
        this.upvoted=new ArrayList<Post>();
        this.downvoted=new ArrayList<Post>();
        this.karma=0;
    }
    /**
     * Add post to posts
     *
     * @param post post that is added the posts
     */
    public void addPost(Post post){
        if(post==null){
            return;
        }
        else{
            posts.add(post);
            this.updateKarma();
        }
    }
    /**
     * Update the user's karma
     */
    public void updateKarma(){
        if(posts==null){
            return;
        }
        int upvoteCount=0;
        int downvoteCount=0;
        for(int i=0;i<posts.size();i++){
            upvoteCount+=posts.get(i).getUpvoteCount();
            downvoteCount+=posts.get(i).getDownvoteCount();
        }
        karma=upvoteCount-downvoteCount;
    }
    /**
     * Get the karma of the user
     *
     * @return karma of the user
     */
    public int getKarma(){
        return karma;
    }
    /**
     * Add post to upvoted accordingly
     *
     * @param post post that's added
     */
    public void upvote(Post post){
        if(post==null){
            return;
        }
        for(int i=0;i<upvoted.size();i++){
            User username=new User(this.username);
            // if post has been upvoted or the author of the post is the user 
            if(post==upvoted.get(i)||post.getAuthor()==username){
                return;
            }
        }
        for(int i=0;i<downvoted.size();i++){
            if(post==downvoted.get(i)){
                downvoted.remove(post);
                post.updateDownvoteCount(false);
                upvoted.add(post);      
                post.updateUpvoteCount(true);
                post.getAuthor().updateKarma();
                return;
            }
        }
        upvoted.add(post);
        post.updateUpvoteCount(true);
        post.getAuthor().updateKarma();
    }
    /**
     * Add post to downvoted accordingly
     *
     * @param post post that's added
     */
    public void downvote(Post post){
        if(post==null){
            return;
        }
        for(int i=0;i<downvoted.size();i++){
            User username=new User(this.username);
            if(post==downvoted.get(i)||post.getAuthor()==username){
                return;
            }
        }
        for(int i=0;i<upvoted.size();i++){
            if(post==upvoted.get(i)){
                upvoted.remove(post);
                post.updateUpvoteCount(false);
                downvoted.add(post);      
                post.updateDownvoteCount(true);
                post.getAuthor().updateKarma();
                return;
            }
        }
        downvoted.add(post);
        post.updateDownvoteCount(true);
        post.getAuthor().updateKarma();
        return;
    }
    /**
     * Get the user's post with the greatest karma value
     *
     * @return top post of the user
     */
    public Post getTopPost(){
        ArrayList<Post> postsOnlyComment = new ArrayList<Post>();
        for(int i=0;i<posts.size();i++){
            if(posts.get(i).getTitle()==null){
                postsOnlyComment.add(posts.get(i));
            }
        }
        // leave only post in the posts
        posts.removeAll(postsOnlyComment);
        if(posts.size()==0){
            return null;
        }
        else{
            Post topPost=posts.get(0);
            int max=posts.get(0).getUpvoteCount()-posts.get(0).getDownvoteCount();
            // read posts backwards in order to get the first post if there's a tie
            for(int i=posts.size()-1;i>=0;i--){
                if(posts.get(i).getUpvoteCount()-posts.get(i).getDownvoteCount()>=max){
                    topPost=posts.get(i);
                    max=posts.get(i).getUpvoteCount()-posts.get(i).getDownvoteCount();
                }
            }
            return topPost;
        }
    }
    /**
     * Get the user's comment with the greatest karma value
     *
     * @return top comment of the user
     */
    public Post getTopComment(){
        ArrayList<Post> postsOnlyPost = new ArrayList<Post>();
        for(int i=0;i<posts.size();i++){
            if(posts.get(i).getTitle()!=null){
                postsOnlyPost.add(posts.get(i));
            }
        }
        // leave only comment in the posts
        posts.removeAll(postsOnlyPost);
        if(posts.size()==0){
            return null;
        }
        else{
            Post topComment=posts.get(0);
            int max=posts.get(0).getUpvoteCount()-posts.get(0).getDownvoteCount();
            for(int i=posts.size()-1;i>=0;i--){
                if(posts.get(i).getUpvoteCount()-posts.get(i).getDownvoteCount()>=max){
                    topComment=posts.get(i);
                    max=posts.get(i).getUpvoteCount()-posts.get(i).getDownvoteCount();
                }
            }
            return topComment;
        }
    }
    /**
     * Get the user's posts
     *
     * @return list of the user's post(s)
     */
    public ArrayList<Post> getPosts(){    
        return posts;
    }
    /**
     * Convert username to string format
     * 
     * @return username in string format 
     */
    public String toString(){
        String TO_STRING_USER_FORMAT="u/%s Karma: %d";
        String user=String.format(TO_STRING_USER_FORMAT,username,karma);
        return user;
    }
}
