package com.example.algosparkproject.data;


import com.example.algosparkproject.models.Exercise;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;



    public class ExerciseRepository {

        public ExerciseRepository() {
            // No initialization needed
        }

        // You can keep the submitCode method if you are still using it.
        // If the submitcode method has been moved to the gemini manager you can remove it.

        public interface ExerciseRepositoryCallback {
            void onSuccess(String feedback);
            void onError(Throwable error);
        }
    }
