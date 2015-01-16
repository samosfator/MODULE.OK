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
import ua.samosfator.moduleok.recyclerview.adapter.SubjectItemAdapter;

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

    private void initSubjects() {
        try {
            int semester = StudentKeeper.getCurrentSemesterIndex();
            mSubjects.clear();
            mSubjects.addAll(StudentKeeper.getCurrentStudent().getSemesters().get(semester).getSubjects());
        } catch (IllegalArgumentException e) {
            openLoginFragment();
        }
    }

    private void openLoginFragment() {
        getFragmentManager().beginTransaction()
                .replace(R.id.main_container, new LoginFragment())
                .commit();
    }

    public void onEvent(RefreshEvent event) {
        StudentKeeper.refreshStudent();
        initSubjects();
        rerenderSubjectsList();
    }

    public void onEvent(SemesterChangedEvent event) {
        Log.d("[SubjectsFragment#onEvent(SemesterChangedEvent)]",
                "Current semesterIndex:" + StudentKeeper.getCurrentSemesterIndex());

        initSubjects();
        rerenderSubjectsList();
    }

    private void rerenderSubjectsList() {
        mSectionAdapter.notifyItemRangeChanged(0, mSectionAdapter.getItemCount());
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
}
