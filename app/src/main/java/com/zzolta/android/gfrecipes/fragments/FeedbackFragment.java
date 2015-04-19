package com.zzolta.android.gfrecipes.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;
import com.zzolta.android.gfrecipes.R;
import com.zzolta.android.gfrecipes.activities.MainActivity;
import com.zzolta.android.gfrecipes.utils.ApplicationConstants;

/*
 * Copyright (C) 2015 Zolta Szekely
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class FeedbackFragment extends Fragment {

    private String userName;
    private String body;
    private int type;
    private boolean wantsResponse;
    private boolean twoPane;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final Activity activity = getActivity();
        if (activity instanceof MainActivity) {
            twoPane = ((MainActivity) activity).isTwoPane();
        }
        final ScrollView feedbackForm = (ScrollView) inflater.inflate(R.layout.feedback_form, container, false);
        final Spinner feedbackSpinner = (Spinner) feedbackForm.findViewById(R.id.feedback_type);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(activity, R.array.feedback_type_list, R.layout.spinner_item);
        feedbackSpinner.setAdapter(adapter);
        final Button sendFeedback = (Button) feedbackForm.findViewById(R.id.send_feedback);
        sendFeedback.setOnClickListener(new SendFeedbackClickListener());
        setHasOptionsMenu(true);
        if (twoPane) {
            final View viewById = activity.findViewById(R.id.recipe_detail);
            final LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) viewById.getLayoutParams();
            params.weight = 0;
            viewById.setLayoutParams(params);
        }
        return feedbackForm;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        saveState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final Activity activity = getActivity();
        if (activity instanceof MainActivity) {
            final MainActivity mainActivity = (MainActivity) activity;
            mainActivity.onSectionAttached(getArguments().getInt(ApplicationConstants.ARG_SECTION_NUMBER));
            mainActivity.getSupportActionBar().setBackgroundDrawable(this.getResources().getDrawable(R.drawable.ab_solid_example));
        }
        if (savedInstanceState != null) {
            restoreState(savedInstanceState, activity);
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        final MenuItem searchMenu = menu.findItem(R.id.search);
        if (searchMenu != null) {
            searchMenu.setVisible(false);
        }
    }

    private void saveState(Bundle outState) {
        outState.putString(ApplicationConstants.USER_NAME, userName);
        outState.putInt(ApplicationConstants.TYPE, type);
        outState.putString(ApplicationConstants.BODY, body);
        outState.putBoolean(ApplicationConstants.WANTS_RESPONSE, wantsResponse);
    }

    private void restoreState(Bundle savedInstanceState, Activity activity) {
        final EditText userNameField = (EditText) activity.findViewById(R.id.feedback_user_name);
        userName = savedInstanceState.getString(ApplicationConstants.USER_NAME);
        userNameField.setText(userName);
        final EditText detailsField = (EditText) activity.findViewById(R.id.feedback_body);
        body = savedInstanceState.getString(ApplicationConstants.BODY);
        detailsField.setText(body);
        final Spinner feedbackSpinner = (Spinner) activity.findViewById(R.id.feedback_type);
        type = savedInstanceState.getInt(ApplicationConstants.TYPE);
        feedbackSpinner.setSelection(type);
        final Checkable responseCheckbox = (Checkable) activity.findViewById(R.id.wants_response);
        wantsResponse = savedInstanceState.getBoolean(ApplicationConstants.WANTS_RESPONSE);
        responseCheckbox.setChecked(wantsResponse);
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
