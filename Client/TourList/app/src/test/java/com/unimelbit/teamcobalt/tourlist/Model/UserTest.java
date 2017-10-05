package com.unimelbit.teamcobalt.tourlist.Model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;

/**
 * Created by awhite on 5/10/17.
 */

@RunWith(RobolectricTestRunner.class)
public class UserTest {

    User user;
    String id;
    String username;
    String password;
    String email;
    ArrayList<String> savedtrips;
    ArrayList<String> favouritetrips;

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

    @Test
    public void newUserArrayFromJSON() throws Exception {

    }

    @Test
    public void getUserRegistration() throws Exception {
        String expected = "{\"password\":\"" + this.password
                + "\",\"email\":\"" + this.email +
                "\",\"username\":\"" + this.username +
                "\"}";

        assertEquals(expected, user.getUserRegistration());
    }

    @Test
    public void getId() throws Exception {
        assertEquals(user.getId(), this.id);
    }

    @Test
    public void getSavedtrips() throws Exception {
        assertEquals(user.getSavedtrips(), this.savedtrips);
    }

    @Test
    public void getUsername() throws Exception {
        assertEquals(user.getUsername(), this.username);
    }

    @Test
    public void getPassword() throws Exception {
        assertEquals(user.getPassword(), this.password);
    }

    @Test
    public void getEmail() throws Exception {
        assertEquals(user.getEmail(), this.email);
    }

    @Test
    public void getFavouritetrips() throws Exception {
        assertEquals(user.getFavouritetrips(), this.favouritetrips);
    }

}