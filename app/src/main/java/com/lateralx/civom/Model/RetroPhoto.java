package com.lateralx.civom.Model;

import com.google.gson.annotations.SerializedName;

public class RetroPhoto {

    @SerializedName("id")
    private Integer id;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("dae")
    private String dae;
    @SerializedName("fbx")
    private String fbx;
    @SerializedName("thumbnail_compressed")
    private String thumbnail_compressed;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("updated_at")
    private String updated_at;
    @SerializedName("thumbnail_original")
    private String thumbnail_original;


    public RetroPhoto(Integer id, String name, String description, String dae, String fbx, String thumbnail_compressed, String thumbnail_original) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dae = dae;
        this.fbx = fbx;
        this.thumbnail_compressed= thumbnail_compressed;
        this.thumbnail_original = thumbnail_original;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDae() {
        return dae;
    }

    public void setDae(String dae) {
        this.dae = dae;
    }

    public String getFbx() {
        return fbx;
    }

    public void setFbx(String fbx) {
        this.fbx = fbx;
    }

    public String getThumbnail_compressed() {
        return thumbnail_compressed;
    }

    public void setThumbnail_compressed(String thumbnail_compressed) {
        this.thumbnail_compressed = thumbnail_compressed;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getThumbnail_original() {
        return thumbnail_original;
    }

    public void setThumbnail_original(String thumbnail_original) {
        this.thumbnail_original = thumbnail_original;
    }
}
