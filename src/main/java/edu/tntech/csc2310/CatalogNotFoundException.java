package edu.tntech.csc2310;

public class CatalogNotFoundException extends Exception
{
    public CatalogNotFoundException()
    {
        super();
    }

    public CatalogNotFoundException(String message)
    {
        super(message);
    }

    @Override
    public String getMessage()
    {
        return "Catalog Search: " + super.getMessage();
    }

    @Override
    public String toString()
    {
        return this.getMessage();
    }
}
