package ua.samosfator.moduleok.fragment.semesters_subjects_fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.splunk.mint.Mint;
import com.splunk.mint.MintLogLevel;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import ua.samosfator.moduleok.R;
import ua.samosfator.moduleok.StudentKeeper;
import ua.samosfator.moduleok.animation.AnimationFactory;
import ua.samosfator.moduleok.event.RefreshEvent;
import ua.samosfator.moduleok.event.SemesterChangedEvent;
import ua.samosfator.moduleok.fragment.LoginFragment;
import ua.samosfator.moduleok.parser.Subject;
import ua.samosfator.moduleok.recyclerview.RecyclerItemClickListener;

public class SubjectsFragment extends Fragment {

    private List<Subject> mSubjects = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private SubjectItemAdapter mSectionAdapter;

    public SubjectsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        Mint.logEvent("view SubjectsFragment", MintLogLevel.Info);
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_subjects, container, false);
        initSubjects();
        initSectionAdapter();
        initRecyclerView(rootView);
        return rootView;
    }

    private void initSectionAdapter() {
        mSectionAdapter = new SubjectItemAdapter(getActivity(), mSubjects);
    }

    private void initRecyclerView(View rootView) {
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.subjects_recycler_view);
        mRecyclerView.setAdapter(mSectionAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                setSubjectTotalScore(view, position);
                animateSubjectTotalScoreChange(view);
            }

            private void animateSubjectTotalScoreChange(View view) {
                ViewFlipper viewFlipper = (ViewFlipper) view.findViewById(R.id.subject_score_view_flipper);
                AnimationFactory.flipTransition(viewFlipper, AnimationFactory.FlipDirection.LEFT_RIGHT);
            }

            private void setSubjectTotalScore(View view, int position) {
                TextView subjectTotalScoreTextView = (TextView) view.findViewById(R.id.subject_total_score);
                subjectTotalScoreTextView.setText(String.valueOf(mSubjects.get(position).getTotalScore()));
            }
        }));
    }

    private void initSubjects() {
        try {
            int semester = StudentKeeper.getCurrentSemesterIndex();
            mSubjects.clear();
            mSubjects.addAll(StudentKeeper.getCurrentStudent().getSemesters().get(semester).getSubjects());
        } catch (IllegalArgumentException e) {
            openLoginFragment();
        }
        reRenderSubjectsList();
    }

    private void reRenderSubjectsList() {
        mSectionAdapter.notifyItemRangeChanged(0, mSectionAdapter.getItemCount());
    }

    private void openLoginFragment() {
        getFragmentManager().beginTransaction()
                .replace(R.id.main_container, new LoginFragment())
                .commit();
    }

    public void onEvent(RefreshEvent event) {
        StudentKeeper.refreshStudent();
        initSubjects();
    }

    public void onEvent(SemesterChangedEvent event) {
        Log.d("SEMESTER_CHANGED_EVENT", "SemesterIndex:" + StudentKeeper.getCurrentSemesterIndex());

        initSubjects();
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
}
