package com.pix.http;

public abstract class ResultParser<T extends Object> {


    private ErrorObject mErrorObject;

    public abstract Object resolve(T result) throws Exception;

    private HttpRequester mHttpRequester;

    public Object getTag() {
        return object;
    }

    public void setTag(Object object) {
        this.object = object;
    }

    private Object object;




    public void setErrorObject(ErrorObject errorObject) {
        this.mErrorObject = errorObject;
    }

    public ErrorObject getErrorObject() {
        return this.mErrorObject;
    }

    public int getErrorCodeID() {
        if (mErrorObject == null) {
            return 0;
        }
        return mErrorObject.getErrorID();
    }

    public boolean isSuccess() {
        return mErrorObject == null;
    }


    public void setmHttpRequester(HttpRequester mHttpRequester) {
        this.mHttpRequester = mHttpRequester;
    }

    public int getCode() {
        return mErrorObject != null ? mErrorObject.getCode() : 0;
    }

    public String getMessage() {
        return mErrorObject != null ? mErrorObject.getMessage() : "";
    }
}
