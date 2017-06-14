package edu.umd.cs.agileandroid;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.io.File;

import edu.umd.cs.agileandroid.model.Story;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


public class StoryFragment extends Fragment {
    private final String TAG = getClass().getSimpleName();

    private static final String EXTRA_STORY_CREATED = "StoryCreated";
    private static final String ARG_STORY_ID = "StoryId";
    private static final int REQUEST_CODE_CREATE_PHOTO = 1;

    private Story story;

    private EditText summaryEditText;
    private EditText acceptanceCriteriaEditText;
    private EditText storyPointsEditText;
    private RadioGroup priorityRadioGroup;
    private Spinner statusSpinner;
    private ImageButton takePicture;
    private Button saveButton;
    private Button cancelButton;
    private ImageView photo;
    private File myPhoto;
    private Bitmap bitmap;
    public static StoryFragment newInstance(String storyId) {
        Bundle args = new Bundle();
        args.putString(ARG_STORY_ID, storyId);

        StoryFragment fragment = new StoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String storyId = getArguments().getString(ARG_STORY_ID);
        story = DependencyFactory.getStoryService(getActivity().getApplicationContext()).getStoryById(storyId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_story, container, false);

        summaryEditText = (EditText)view.findViewById(R.id.summary);
        if (story != null) {
            summaryEditText.setText(story.getSummary());
        }

        acceptanceCriteriaEditText = (EditText)view.findViewById(R.id.criteria);
        if (story != null) {
            acceptanceCriteriaEditText.setText(story.getAcceptanceCriteria());
        }

        storyPointsEditText = (EditText)view.findViewById(R.id.points);
        if (story != null) {
            storyPointsEditText.setText("" + story.getStoryPoints());
        }

        priorityRadioGroup = (RadioGroup)view.findViewById(R.id.radio_group);
        if (story != null) {
            switch (story.getPriority()) {
                case CURRENT:
                    priorityRadioGroup.check(R.id.radio_current);
                    break;
                case NEXT:
                    priorityRadioGroup.check(R.id.radio_next);
                    break;
                case LATER:
                    priorityRadioGroup.check(R.id.radio_later);
                    break;
                default:
                    priorityRadioGroup.check(R.id.radio_later);
                    break;
            }
        } else {
            priorityRadioGroup.check(R.id.radio_later);
        }

        statusSpinner = (Spinner)view.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.status_array, android.R.layout.simple_spinner_item);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(statusAdapter);
        if (story != null) {
            statusSpinner.setSelection(story.getStatusPosition());
        }
        photo = (ImageView)view.findViewById(R.id.photo);
        myPhoto = getPhotoFile();
//        if (myPhoto != null) {
//
//            String path = myPhoto.getPath();
//            bitmap = BitmapFactory.decodeFile(path);
//
//            photo.setImageBitmap(bitmap);
//            bitmap = BitmapFactory.decodeFile(myPhoto.getPath());
//            if (bitmap != null) {
//                photo.setImageBitmap(bitmap);
//            }
//        }

        saveButton = (Button)view.findViewById(R.id.save_story_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inputsAreValid()) {
                    if (story == null) {
                        story = new Story();
                    }

                    story.setSummary(summaryEditText.getText().toString());
                    story.setAcceptanceCriteria(acceptanceCriteriaEditText.getText().toString());
                    story.setStoryPoints(Double.parseDouble(storyPointsEditText.getText().toString()));

                    int priorityId = priorityRadioGroup.getCheckedRadioButtonId();
                    switch (priorityId) {
                        case R.id.radio_current:
                            story.setPriorityCurrent();
                            break;
                        case R.id.radio_next:
                            story.setPriorityNext();
                            break;
                        case R.id.radio_later:
                            story.setPriorityLater();
                            break;
                        default:
                            story.setPriorityLater();
                            break;
                    }

                    story.setStatus(statusSpinner.getSelectedItemPosition());

                    Intent data = new Intent();
                    data.putExtra(EXTRA_STORY_CREATED, story);
                    getActivity().setResult(RESULT_OK, data);
                    getActivity().finish();
                }
            }
        });

        cancelButton = (Button)view.findViewById(R.id.cancel_story_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().setResult(RESULT_CANCELED);
                getActivity().finish();
            }
        });

        takePicture = (ImageButton) view.findViewById(R.id.camera);

        takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    myPhoto = getPhotoFile();

                    if (myPhoto == null &&
                            i.resolveActivity(getActivity().getPackageManager()) != null) {
                        takePicture.setEnabled(false);
                    } else {
                        i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(myPhoto));
                        startActivityForResult(i, REQUEST_CODE_CREATE_PHOTO);
                        getActivity().setResult(RESULT_OK, i);
//                        getActivity().finish();
                    }
            }

        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_CODE_CREATE_PHOTO) {

//            if (data == null) return;
            bitmap = BitmapFactory.decodeFile(myPhoto.getPath());
            photo.setImageBitmap(bitmap);
        }

    }

    private File getPhotoFile() {
        File externalPhotoDir = getActivity().
                getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        if (externalPhotoDir == null) return null;

        return new File(externalPhotoDir, "IMG_" + System.currentTimeMillis() + ".jpg");
    }
    public static Story getStoryCreated(Intent data) {
        return (Story)data.getSerializableExtra(EXTRA_STORY_CREATED);
    }

    private boolean inputsAreValid() {
        return
                summaryEditText.getText().toString().length() > 0 &&
                acceptanceCriteriaEditText.getText().toString().length() > 0 &&
                storyPointsEditText.getText().toString().length() > 0 &&
                statusSpinner.getSelectedItemPosition() >= 0;
    }
}
