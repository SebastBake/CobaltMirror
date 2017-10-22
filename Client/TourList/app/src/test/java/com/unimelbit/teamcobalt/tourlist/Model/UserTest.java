package com.unimelbit.teamcobalt.tourlist.Model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;

/**
 * Tests for the User class.
 * User is used to represent a user
 */

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class UserTest {

    User user;
    String id;
    String username;
    String password;
    String email;
    ArrayList<String> savedtrips;
    ArrayList<String> favouritetrips;

    /*
     * Creates a User before each test to be used for testing
     */
    @Before
    public void setUp() throws Exception {
        this.id = "0";
        this.username = "username";
        this.password = "password";
        this.email = "email";
        this.savedtrips = new ArrayList<>();
        this.savedtrips.add("saved trip");
        this.favouritetrips = new ArrayList<>();
        this.favouritetrips.add("favourite trip");

        this.user = new User(
                this.id,
                this.username,
                this.password,
                this.email,
                this.savedtrips,
                this.favouritetrips);
    }

    /*
     * Test that a new user is created to match the provided json
     */
    @Test
    public void newUserArrayFromJSON() throws Exception {
//        String expected = "{\"password\":\"" + this.password
//                + "\",\"email\":\"" + this.email +
//                "\",\"username\":\"" + this.username +
//                "\"}";
    }

    /*
     * Getter for the id field
     */
    @Test
    public void getId() throws Exception {
        assertEquals(user.getId(), this.id);
    }

    /*
     * Getter for the saved trips field
     */
    @Test
    public void getSavedtrips() throws Exception {
        assertEquals(user.getSavedtrips(), this.savedtrips);
    }

    /*
     * Getter for the username field
     */
    @Test
    public void getUsername() throws Exception {
        assertEquals(user.getUsername(), this.username);
    }

    /*
     * Getter for the password field
     */
    @Test
    public void getPassword() throws Exception {
        assertEquals(user.getPassword(), this.password);
    }

    /*
     * Getter for the email field
     */
    @Test
    public void getEmail() throws Exception {
        assertEquals(user.getEmail(), this.email);
    }

    /*
     * Getter for the favourite trips field
     */
    @Test
    public void getFavouritetrips() throws Exception {
        assertEquals(user.getFavouritetrips(), this.favouritetrips);
    }

}
