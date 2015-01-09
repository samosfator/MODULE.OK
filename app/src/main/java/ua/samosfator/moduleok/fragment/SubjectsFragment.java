package ua.samosfator.moduleok.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.util.List;

import de.greenrobot.event.EventBus;
import ua.samosfator.moduleok.Auth;
import ua.samosfator.moduleok.R;
import ua.samosfator.moduleok.animation.AnimationFactory;
import ua.samosfator.moduleok.event.RefreshEvent;
import ua.samosfator.moduleok.parser.Subject;
import ua.samosfator.moduleok.recyclerview.RecyclerItemClickListener;
import ua.samosfator.moduleok.recyclerview.adapter.SubjectItemAdapter;

public class SubjectsFragment extends Fragment {

    private List<Subject> mSubjects;

    private RecyclerView mRecyclerView;
    private SubjectItemAdapter mSectionAdapter;

    public SubjectsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_subjects, container, false);
        tryInitSubjects();
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

                System.out.println(subjectTotalScoreTextView.getText().toString());
                System.out.println(mSubjects);

                subjectTotalScoreTextView.setText(String.valueOf(mSubjects.get(position).getTotalScore()));
            }
        }));
        return rootView;
    }

    private void tryInitSubjects() {
        try {
            int semester = getArguments().getInt("semester");
            mSubjects = Auth.getCurrentStudent().getSemesters().get(semester).getSubjects();
        } catch (IllegalArgumentException e) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.main_container, new LoginFragment())
                    .commit();
        }
    }

    public void onEvent(RefreshEvent event) {
        Auth.refreshStudent();
        tryInitSubjects();
        mSectionAdapter.notifyItemRangeChanged(0, mSectionAdapter.getItemCount());
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
}
