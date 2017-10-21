package com.unimelbit.teamcobalt.tourlist;

import android.support.v4.app.Fragment;

import com.unimelbit.teamcobalt.tourlist.Chat.ChatListRoomFragment;
import com.unimelbit.teamcobalt.tourlist.CreateOrEditTrip.TabbedCreateOrEditTripFragment;
import com.unimelbit.teamcobalt.tourlist.Home.HomeFragment;
import com.unimelbit.teamcobalt.tourlist.Home.LoginFragment;
import com.unimelbit.teamcobalt.tourlist.Home.LoginOrRegisterFragment;
import com.unimelbit.teamcobalt.tourlist.Home.ProfileFragment;
import com.unimelbit.teamcobalt.tourlist.Home.RegisterFragment;
import com.unimelbit.teamcobalt.tourlist.Model.Trip;
import com.unimelbit.teamcobalt.tourlist.ServerRequester.LoadingFragment;
import com.unimelbit.teamcobalt.tourlist.TripDetails.TripGetRequest;
import com.unimelbit.teamcobalt.tourlist.TripSearch.TripSearchFragment;
import com.unimelbit.teamcobalt.tourlist.TripSearch.TripSearchGetRequest;
import com.unimelbit.teamcobalt.tourlist.TripSearch.TripSearchResultFragment;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.isA;
import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyNew;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;
import static org.powermock.api.mockito.PowerMockito.whenNew;
import static org.powermock.api.support.membermodification.MemberMatcher.method;

/**
 * Created by awhite on 17/10/17.
 */
@RunWith(RobolectricTestRunner.class)
@PowerMockIgnore({ "org.mockito.*", "org.robolectric.*", "android.*", "org.powermock.*"})
@PrepareForTest(BaseFragmentContainerManager.class)
@Config(manifest=Config.NONE)
public class  BaseFragmentContainerManagerTest {

    @Rule
    public PowerMockRule rule = new PowerMockRule();

    BaseActivity base;
    int containerId;
    BaseFragmentContainerManager manager;

    @Before
    public void setUp() throws Exception {
        base = mock(BaseActivity.class);
        containerId = 0;

//        suppress(Whitebox.getMethod(BaseFragmentContainerManager.class, "replaceFragment", Fragment.class));
        manager = spy(new BaseFragmentContainerManager(base, containerId));
        doNothing().when(manager, method(BaseFragmentContainerManager.class, "replaceFragment")).withArguments(isA(Fragment.class));

    }

    @Test
    public void getContainerId() throws Exception {
        assertEquals(manager.getContainerId(), containerId);
    }

    @Test
    public void gotoRegisterFragment() throws Exception {

        manager.gotoRegisterFragment();
        verifyPrivate(manager).invoke("gotoFragmentUsingBackstack", isA(RegisterFragment.class));

    }

    @Test
    public void gotoRegisterFragmentWithMessage() throws Exception {

        manager.gotoRegisterFragmentWithMessage("msg");
        verifyPrivate(manager).invoke("gotoFragmentUsingBackstack", isA(RegisterFragment.class));
    }

    @Test
    public void gotoLoginFragment() throws Exception {

        manager.gotoLoginFragment();
        verifyPrivate(manager).invoke("gotoFragmentUsingBackstack", isA(LoginFragment.class));
    }

    @Test
    public void gotoLoginFragmentWithMessage() throws Exception {

        manager.gotoLoginFragmentWithMessage("msg");
        verifyPrivate(manager).invoke("gotoFragmentUsingBackstack", isA(LoginFragment.class));
    }

    @Test
    public void gotoProfileFragment() throws Exception {

        manager.gotoProfileFragment();
        verifyPrivate(manager).invoke("gotoFragmentUsingBackstack", isA(ProfileFragment.class));

    }

    @Test
    public void gotoLoginOrRegisterFragment() throws Exception {

        manager.gotoLoginOrRegisterFragment();
        verifyPrivate(manager).invoke("gotoFragmentUsingBackstack", isA(LoginOrRegisterFragment.class));

    }

    @Test
    public void gotoLoginOrRegisterFragmentWithMessage() throws Exception {

        manager.gotoLoginOrRegisterFragmentWithMessage("msg");
        verifyPrivate(manager).invoke("gotoFragmentUsingBackstack", isA(LoginOrRegisterFragment.class));
    }

    @Test
    public void gotoHomeFragmentWithMessage() throws Exception {

        manager.gotoHomeFragmentWithMessage("msg");
        verifyPrivate(manager).invoke("gotoFragmentUsingBackstack", isA(HomeFragment.class));
    }

    @Test
    public void gotoHomeFragment() throws Exception {

        manager.gotoHomeFragment();
        verifyPrivate(manager).invoke("gotoFragmentUsingBackstack", isA(HomeFragment.class));
    }

    @Test
    public void gotoTabbedTripFragment() throws Exception {

        TripGetRequest trip = mock(TripGetRequest.class);
        whenNew(TripGetRequest.class).withAnyArguments().thenReturn(trip);
        manager.gotoTabbedTripFragment("url");
        verifyNew(TripGetRequest.class).withArguments("url", manager);
    }

    @Test
    public void gotoTripSearchFragment() throws Exception {

        manager.gotoTripSearchFragment();
        verifyPrivate(manager).invoke("gotoFragmentUsingBackstack", isA(TripSearchFragment.class));
    }

    @Test
    public void gotoTripSearchResultFragment() throws Exception {

        TripSearchGetRequest request = mock(TripSearchGetRequest.class);
        manager.gotoTripSearchResultFragment("", request);
        verifyPrivate(manager).invoke("gotoFragmentUsingBackstack", isA(TripSearchResultFragment.class));

    }

    @Test
    public void gotoCreateTrip() throws Exception {

        manager.gotoCreateTrip();
        verifyPrivate(manager).invoke("gotoFragmentUsingBackstack", isA(TabbedCreateOrEditTripFragment.class));

    }

    @Test
    public void gotoEditTrip() throws Exception {

        Trip trip = mock(Trip.class);
        manager.gotoEditTrip(trip);
        verifyPrivate(manager).invoke("gotoFragmentUsingBackstack", isA(TabbedCreateOrEditTripFragment.class));

    }

    @Test
    public void gotoLoadingFragment() throws Exception {

        manager.gotoLoadingFragment("loading msg");
        verifyPrivate(manager).invoke("gotoFragmentUsingBackstack", isA(LoadingFragment.class));

    }

    @Test
    public void goToChatRooms() throws Exception {

        manager.goToChatRooms();
        verifyPrivate(manager).invoke("gotoFragmentUsingBackstack", isA(ChatListRoomFragment.class));

    }

    @Test
    public void getBaseActivity() throws Exception {
        assertEquals(manager.getBaseActivity(), base);
    }

}