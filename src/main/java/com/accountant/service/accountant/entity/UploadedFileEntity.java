package com.accountant.service.accountant.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "uploaded_file")
public class UploadedFileEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    @Column(name = "file_name")
    private String fileName;
    @Column(name = "file_content", columnDefinition = "text")
    private String fileContent;
    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    public UploadedFileEntity() {
    }

    public UploadedFileEntity(String fileName, String fileContent, Date creationDate) {
        this.fileName = fileName;
        this.fileContent = fileContent;
        this.creationDate = creationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileContent() {
        return fileContent;
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
