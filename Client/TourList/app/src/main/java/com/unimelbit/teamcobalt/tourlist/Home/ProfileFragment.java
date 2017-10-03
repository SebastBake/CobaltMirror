 import android.view.LayoutInflater;
  import android.view.View;
  import android.view.ViewGroup;
 -import android.widget.TextView;
  
 -import com.unimelbit.teamcobalt.tourlist.BaseActivity;
 -import com.unimelbit.teamcobalt.tourlist.Model.User;
  import com.unimelbit.teamcobalt.tourlist.R;
  
  public class ProfileFragment extends Fragment {
 public View onCreateView(LayoutInflater inflater, ViewGroup container,
          View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
          getActivity().setTitle("Profile");
  
 -       initProfileTextBoxes(rootView);
 
          return rootView;
      }
 
     private void initProfileTextBoxes(View rootView) {
 
         User user = ((BaseActivity)getActivity()).getCurrentUser();
 
         TextView loggedInAsMsg = (TextView) rootView.findViewById(R.id.profile_you_are_signed_in_message);
         TextView userNameTextBox = (TextView) rootView.findViewById(R.id.profile_username);
         TextView emailTextBox = (TextView) rootView.findViewById(R.id.profile_username);
 
        if(user != null) {
 
             loggedInAsMsg.setText("You're logged in as:");
             userNameTextBox.setText(user.getUsername());
             emailTextBox.setText(user.getEmail());
         } else {
             loggedInAsMsg.setText("You aren't logged in right now...");
        }
     }
  }
