package com.skoruz.amwell.constants;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by SKoruz-Keerthi on 08-10-2015.
 */
public class MediaSRO implements Serializable{

        String duration;
        String mediaPath;
        String mediaType;
        String mimeType;
        String name;
        byte[] fileData;

        public MediaSRO() {}

        public MediaSRO(JSONObject paramJSONObject)
        {
            setMediaPath(paramJSONObject.optString("path"));
            setMediaType(paramJSONObject.optString("type"));
            setDuration(paramJSONObject.optString("duration"));
            setMimeType(paramJSONObject.optString("mimeType"));
        }

        public String getDuration()
        {
            return this.duration;
        }

        public String getMediaPath()
        {
            return this.mediaPath;
        }

        public String getMediaType()
        {
            return this.mediaType;
        }

        public String getMimeType()
        {
            return this.mimeType;
        }

        public String getName()
        {
            return this.name;
        }

        public void setDuration(String paramString)
        {
            this.duration = paramString;
        }

        public void setMediaPath(String paramString)
        {
            this.mediaPath = paramString;
        }

        public void setMediaType(String paramString)
        {
            this.mediaType = paramString;
        }

        public void setMimeType(String paramString)
        {
            this.mimeType = paramString;
        }

        public void setName(String paramString)
        {
            this.name = paramString;
        }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }
}
