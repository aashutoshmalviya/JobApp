package com.illusion.jobapprest.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Scope("prototype")
@Entity
@Table(name = "job")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Jobs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "jobid")
    private Integer jobId;

    @Column(name = "jobname")
    private String jobName;

    @Column(name = "jobdesc")
    private String jobDesc;

    @Column(name = "reqexp")
    private Integer reqExp;

    @Column(name = "techstack")
    @Convert(converter = StringListConverter.class)
    private List<String> techStack;

    @Override
    public String toString() {
        return "Jobs{" +
                "jobId=" + jobId +
                ", jobName='" + jobName + '\'' +
                ", jobDesc='" + jobDesc + '\'' +
                ", requiredExperience=" + reqExp +
                ", techstack=" + techStack +
                '}';
    }

    @Converter
    public static class StringListConverter implements AttributeConverter<List<String>, String> {
        private static final String SPLIT_CHAR = ",";

        @Override
        public String convertToDatabaseColumn(List<String> stringList) {
            return stringList != null ? String.join(SPLIT_CHAR, stringList) : "";
        }

        @Override
        public List<String> convertToEntityAttribute(String string) {
            return string != null ? Arrays.asList(string.split(SPLIT_CHAR)) : null;
        }
    }
}
