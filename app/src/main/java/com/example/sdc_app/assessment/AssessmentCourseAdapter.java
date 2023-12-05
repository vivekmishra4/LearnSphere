package com.example.sdc_app.assessment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sdc_app.R;
import java.util.List;

public class AssessmentCourseAdapter extends RecyclerView.Adapter<AssessmentCourseAdapter.AssessmentHolder> {
    private List<AssessmentItem> courses;
    private OnItemClickListener listener;
    Context context;
    public AssessmentCourseAdapter(List<AssessmentItem>  courses,OnItemClickListener listener){
        this.courses=courses;
        this.listener=listener;

    }
    @NonNull
    @Override
    public AssessmentCourseAdapter.AssessmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.assessment_course,parent,false);
        context=parent.getContext();
        return new AssessmentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentCourseAdapter.AssessmentHolder holder, int position) {
        AssessmentItem course=courses.get(position);
        holder.courseName.setText(course.getCourseName());
        holder.offeredBy.setText(course.getOfferedBy());
        holder.score.setText(course.getScore().toString());
        holder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final boolean[] proceed = new boolean[1];
                proceed[0]=false;
                showAlertDialog(context, new AlertDialogCallback() {
                    @Override
                    public void onUserResponse(boolean userClickedYes) {
                        // Handle user's response
                        proceed[0]=userClickedYes;
                        if(proceed[0]){
                            listener.onItemClick(holder.Delete,course.getCourseId());

                        }
                    }
                });





            }
        });
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }
    public interface AlertDialogCallback {
        void onUserResponse(boolean userClickedYes);
    }
    public void showAlertDialog(Context context, final AlertDialogCallback callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Confirmation");
        builder.setMessage("Do you want to Delete the Course?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (callback != null) {
                    callback.onUserResponse(true);
                }
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (callback != null) {
                    callback.onUserResponse(false);
                }
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public class AssessmentHolder extends RecyclerView.ViewHolder {
        TextView courseName;
        TextView offeredBy;
        TextView score;
        ImageView Delete;
        public AssessmentHolder(@NonNull View itemView) {
            super(itemView);
            courseName=itemView.findViewById(R.id.assessment_course_name);
            offeredBy=itemView.findViewById(R.id.assessment_offered_by);
            score=itemView.findViewById(R.id.assessment_course_score);
            Delete=itemView.findViewById(R.id.assessment_delete_course);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(ImageView imageView, String courseId);
    }
}
