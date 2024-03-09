///////////////////////////////////////////////////////////////////////////////
// Title:              Tester
// Files:              Post.java.class User.java.class Tester.java.class
// Quarter:            FALL 2022
//
// Author:             Claire Tsui
// Email:              ctsui@ucsd.edu
// Instructor's Name:  Professor Cao
public class Tester {
    public static void main(String[] args) {
        User u1 = new User("CSE11Student");
        User u2 = new User("Claire");
        User u3 = new User("Claire's mom");
        Post p1 = new Post("Title", "Content", u1);
        Post c1 = new Post("Comment1", p1, u2);
        Post c2 = new Post("Comment2", c1, u1);

        u1.addPost(p1);
        u1.addPost(c2);
        u2.addPost(c1);
        u2.addPost(null);

        u1.upvote(c1);
        u2.downvote(c2);
        u1.downvote(p1);
        u2.downvote(c2);
        u2.upvote(p1);
        u2.upvote(c1);
        u1.upvote(null);
        u2.downvote(null);

        c1.updateUpvoteCount(true);
        p1.updateDownvoteCount(true);
        c2.updateDownvoteCount(false);
        p1.updateDownvoteCount(true);

        u1.updateKarma();
        u2.updateKarma();
        u3.updateKarma();

        System.out.println(c2.getThread());
        System.out.println(p1.getUpvoteCount());
        System.out.println(u1.getPosts());
        System.out.println(u1.getKarma());
        System.out.println(u1.getTopPost());
        System.out.println(u1.getTopComment());
        System.out.println(u2.getPosts());
        System.out.println(u2.getKarma());
        System.out.println(u2.getTopPost());
        System.out.println(u2.getTopComment());
    }
}