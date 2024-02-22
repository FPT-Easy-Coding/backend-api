package com.quizztoast.backendAPI.model.entity.folder;

import com.quizztoast.backendAPI.model.entity.quiz.Quiz;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "quiz_belong_folder")
public class QuizBelongFolder {
    @EmbeddedId
    private QuizBelongFolderId id;
    @Setter
    @Getter
    @Embeddable
    public static class QuizBelongFolderId implements Serializable {

        @ManyToOne
        @JoinColumn(name = "quiz_id")
        private Quiz quiz;

        @ManyToOne
        @JoinColumn(name = "folder_id")
        private Folder folder;
    }
}
