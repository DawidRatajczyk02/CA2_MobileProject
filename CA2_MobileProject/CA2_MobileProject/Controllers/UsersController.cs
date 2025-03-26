using Microsoft.AspNetCore.Mvc;
using CA2_MobileProject.Data;
using CA2_MobileProject.Models;

namespace CA2_MobileProject.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class UsersController : ControllerBase
    {
        private readonly AppDbContext _context;

        public UsersController(AppDbContext context)
        {
            _context = context;
        }

        // POST: Registers a new user and adds to database
        [HttpPost]
        public async Task<ActionResult<User>> RegisterUser(User user)
        {
            _context.Users.Add(user);
            await _context.SaveChangesAsync();

            return CreatedAtAction(null, new { id = user.UserId }, user);
        }

        // GET: Returns a list of all registered users (for admin debugging)
        [HttpGet]
        public async Task<ActionResult<IEnumerable<User>>> GetUsers()
        {
            return Ok(_context.Users.ToList());
        }
    }
}
