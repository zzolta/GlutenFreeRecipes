package com.zzolta.android.glutenfreerecipes.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.*;
import com.zzolta.android.glutenfreerecipes.R;
import com.zzolta.android.glutenfreerecipes.activities.MainActivity;
import com.zzolta.android.glutenfreerecipes.utils.ApplicationConstants;

/**
 * Created by Zolta.Szekely on 2015-04-01.
 */
public class FeedbackFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final LinearLayout feedbackForm = (LinearLayout) inflater.inflate(R.layout.feedback_form, container, false);
        final Spinner feedbackSpinner = (Spinner) feedbackForm.findViewById(R.id.feedback_type);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.feedback_type_list, R.layout.spinner_item);
        feedbackSpinner.setAdapter(adapter);
        final Button sendFeedback = (Button) feedbackForm.findViewById(R.id.send_feedback);
        sendFeedback.setOnClickListener(new SendFeedbackClickListener());
        return feedbackForm;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof MainActivity) {
            ((MainActivity) activity).onSectionAttached(getArguments().getInt(ApplicationConstants.ARG_SECTION_NUMBER));
        }
    }

    private class SendFeedbackClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            sendFeedback();
        }

        private void sendFeedback() {
            final EditText userNameField = (EditText) getActivity().findViewById(R.id.feedback_user_name);
            final String userName = userNameField.getText().toString();

            final EditText detailsField = (EditText) getActivity().findViewById(R.id.feedback_body);
            final String details = detailsField.getText().toString();

            final Spinner feedbackSpinner = (Spinner) getActivity().findViewById(R.id.feedback_type);
            final String feedbackType = feedbackSpinner.getSelectedItem().toString();

            final Checkable responseCheckbox = (Checkable) getActivity().findViewById(R.id.wants_response);
            final boolean wantsResponse = responseCheckbox.isChecked();

            final String emailBody = generateEmailBody(userName, details, wantsResponse);

            doSend(feedbackType, emailBody);
        }

        private String generateEmailBody(String userName, String details, boolean wantsResponse) {
            String body = String.format(ApplicationConstants.GREETING, details);
            if (wantsResponse) {
                body += ApplicationConstants.PLEASE_RESPOND;
            }
            body += String.format(ApplicationConstants.REGARDS, userName);
            return body;
        }

        private void doSend(String feedbackType, String emailBody) {
            final Intent endIntent = new Intent(Intent.ACTION_SEND);
            endIntent.setType(ApplicationConstants.MESSAGE_RFC822);
            endIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{ApplicationConstants.FEEDBACK_EMAIL});
            endIntent.putExtra(Intent.EXTRA_SUBJECT, feedbackType);
            endIntent.putExtra(Intent.EXTRA_TEXT, emailBody);
            try {
                startActivity(Intent.createChooser(endIntent, ApplicationConstants.SEND_MAIL));
            }
            catch (final ActivityNotFoundException ex) {
                Toast.makeText(getActivity(), ApplicationConstants.ERROR_NO_EMAIL_CLIENTS_INSTALLED, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
