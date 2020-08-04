
public enum EnvelopeStatus{
    COMPLETED("Completed"),
    DECLINED("Declined"),
    CREATED("created"),
    SENT("sent");
    public String status;
    
    EnvelopeStatus(String status)
    {
        this.status=status;
    }
    
    public String getvalue()
    {
        return status;
    }
}