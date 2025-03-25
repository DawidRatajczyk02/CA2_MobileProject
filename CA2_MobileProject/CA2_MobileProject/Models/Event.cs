namespace CA2_MobileProject.Models
{
    public class Event
    {
        public int EventId { get; set; }
        public string Title { get; set; }
        public DateTime DateTime { get; set; }
        public string Location { get; set; }
        public string Category { get; set; }
        public string? Description { get; set; }
        public decimal? Price { get; set; }
    }
}
