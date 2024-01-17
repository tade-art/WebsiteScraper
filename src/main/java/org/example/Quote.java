package org.example;

/**
 * The {@code Quote} class represents a quote with text, author, and tags.
 */
public class Quote{
    private String text;
    private String author;
    private String tags;

    /**
     * Gets the text of the quote.
     * @return The text of the quote.
     */
    public String getText(){
        return text;
    }

    /**
     * Sets the text of the quote.
     * @param text The new text for the quote.
     */
    public void setText(String text){
        this.text = text;
    }

    /**
     * Gets the author of the quote.
     * @return The author of the quote.
     */
    public String getAuthor(){
        return author;
    }

    /**
     * Sets the author of the quote.
     * @param author The new author for the quote.
     */
    public void setAuthor(String author){
        this.author = author;
    }

    /**
     * Gets the tags associated with the quote.
     * @return The tags of the quote.
     */
    public String getTags(){
        return tags;
    }

    /**
     * Sets the tags for the quote.
     * @param tags The new tags for the quote.
     */
    public void setTags(String tags){
        this.tags = tags;
    }
}
