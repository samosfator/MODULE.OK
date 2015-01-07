package ua.samosfator.moduleok.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.util.List;

import ua.samosfator.moduleok.Auth;
import ua.samosfator.moduleok.R;
import ua.samosfator.moduleok.animation.AnimationFactory;
import ua.samosfator.moduleok.parser.Subject;
import ua.samosfator.moduleok.recyclerview.RecyclerItemClickListener;
import ua.samosfator.moduleok.recyclerview.adapter.SubjectItemAdapter;

public class SubjectsFragment extends Fragment {

    public static List<Subject> mSubjects;

    private RecyclerView mRecyclerView;
    private SubjectItemAdapter mSectionAdapter;

    public SubjectsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_subjects, container, false);
        mSubjects = Auth.getCurrentStudent().getSemesters().getFirst().getSubjects();
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.subjects_recycler_view);
        mSectionAdapter = new SubjectItemAdapter(getActivity(), mSubjects);
        mRecyclerView.setAdapter(mSectionAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                TextView subjectTotalScoreTextView = (TextView) view.findViewById(R.id.subject_total_score);
                ViewFlipper viewFlipper = (ViewFlipper) view.findViewById(R.id.subject_score_view_flipper);
                AnimationFactory.flipTransition(viewFlipper, AnimationFactory.FlipDirection.LEFT_RIGHT);

                subjectTotalScoreTextView.setText(String.valueOf(mSubjects.get(position).getTotalScore()));
            }
        }));
        return rootView;
    }
}
