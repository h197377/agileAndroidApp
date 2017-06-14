package edu.umd.cs.agileandroid;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import edu.umd.cs.agileandroid.model.Story;
import edu.umd.cs.agileandroid.service.TeamService;
import edu.umd.cs.agileandroid.service.impl.WebTeamService;

/**
 * Created by Beijie on 4/6/2017.
 */

public class TeamFragment extends Fragment {
    private List<String> definitionOfDone;
    private TextView definitions;
    private Button reminder, close;
    private String teamId;
    private WebTeamService wbs;
    public static TeamFragment newInstance() {
        return new TeamFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_team, container, false);

        definitions = (TextView) view.findViewById(R.id.definition);
        reminder = (Button)view.findViewById(R.id.reminder);
        close = (Button)view.findViewById(R.id.close);

        reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),R.string.principle, Toast.LENGTH_SHORT).show();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        updateDefinition();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        wbs = (WebTeamService) DependencyFactory.getTeamService(getContext());
        teamId = "112940416";
        FetchDefinitionAsyncTask fda = new FetchDefinitionAsyncTask();
        fda.execute(teamId);

    }

    private void updateDefinition() {
        if (definitionOfDone != null) {
           for (String s: definitionOfDone) {
               definitions.append(s + System.getProperty("line.separator"));
           }
        }
    }

    public class FetchDefinitionAsyncTask extends AsyncTask<String, Void, List<String>> {


        @Override
        protected void onPostExecute(List<String> strings) {
            super.onPostExecute(strings);
            definitionOfDone = strings;
            updateDefinition();
        }

        @Override
        protected List<String> doInBackground(String... teamId) {
            return wbs.getDefinitionOfDone(teamId[0]);
        }
    }
}
