# CultivApp - Database Setup Complete! 🎉

## ✅ What's Ready

Your CultivApp database is now configured and ready to use!

### Database Type
- **H2 File-Based** with PostgreSQL compatibility
- Location: `server/.data/cultivapp-dev.mv.db`
- **Persists data** across restarts
- **No installation** required

### Complete Schema
✅ 11 tables created  
✅ 40+ sample records loaded  
✅ All relationships and constraints in place  
✅ Indexes configured for performance  

### Sample Data Included
✅ 2 test users (admin + producer)  
✅ 8 plant species  
✅ 1 sample crop  
✅ 2 sample tasks  
✅ Climate data  
✅ System configuration  

## 🚀 Quick Start (2 Steps)

### Step 1: Start Backend
```bash
cd server/src/cultivapp/cultivapp
./mvnw spring-boot:run
```
**Windows:**
```powershell
cd server\src\cultivapp\cultivapp
.\mvnw.cmd spring-boot:run
```

### Step 2: Start Frontend
```bash
cd client
npm install  # First time only
npm run dev
```

## 🔑 Login Credentials

Test with these users:

**Producer Account:**
- Email: `productor@cultivapp.com`
- Password: `password`

**Admin Account:**
- Email: `admin@cultivapp.com`  
- Password: `password`

## 📊 What's in the Database

### Countries & Cities
- 3 countries (Colombia, México, Argentina)
- 5 cities (Bogotá, Medellín, Cali, Ciudad de México, Buenos Aires)

### Plant Species
- Tomate (Tomato)
- Maíz (Corn)
- Papa (Potato)
- Café (Coffee)
- Frijol (Bean)
- Zanahoria (Carrot)
- Lechuga (Lettuce)
- Cebolla (Onion)

### Sample Crop
- **Name:** Tomates Lote A
- **Owner:** Juan Pérez (producer)
- **Status:** Active, in growth stage
- **Area:** 2.5 hectares

## 🔧 Common Tasks

### View Database File
Location: `server/.data/cultivapp-dev.mv.db`

### Reset Database
1. Stop the application
2. Delete: `server/.data/cultivapp-dev.mv.db`
3. Restart - fresh database will be created!

### Change Passwords
Edit `server/src/cultivapp/cultivapp/src/main/resources/data.sql`

Generate new BCrypt hashes at: https://bcrypt-generator.com/ (use strength 10)

## 📚 Documentation

Detailed guides available:
- **Database Setup:** `doc/db-setup.md`
- **Backend README:** `server/readme.md`
- **Implementation Summary:** `DB_IMPLEMENTATION_SUMMARY.md`

## ✨ What's Different from Before

### Before (Old Setup)
❌ In-memory H2 (data lost on restart)  
❌ Old User entity with limited fields  
❌ No proper schema  
❌ DataLoader for seeding  

### Now (New Setup)
✅ File-based H2 (data persists)  
✅ Complete Usuario entity with all fields  
✅ Full schema (11 tables, all relationships)  
✅ SQL scripts (schema.sql + data.sql)  
✅ PostgreSQL compatibility  
✅ Ready for production migration  

## 🎯 Ready For Development

The database layer is ready for these features:

1. ✅ **REQ-001: Login** - Working now!
2. 🚧 **CRUD for Cultivos** - Entities ready
3. 🚧 **Admin Especies Catalog** - Entities ready
4. 🚧 **Task Management** - Entities ready
5. 🚧 **Alert System** - Entities ready

## 🐛 Troubleshooting

### Port 8080 already in use?
Edit `application.yml` and change the port:
```yaml
server:
  port: 8081
```

### Database file not found?
Make sure you're running from the correct directory. The path is relative.

### Data not persisting?
Check that `application-dev.yml` is being used (not in-memory mode).

### Need help?
Check `doc/db-setup.md` for detailed troubleshooting!

## 👥 For Your Team

Everyone can now:
- ✅ Clone the repo
- ✅ Run `./mvnw spring-boot:run`
- ✅ Start coding immediately
- ✅ No database setup required!

## 📝 Note About Passwords

The sample users both use the simple password `"password"` for easy testing during development.

**For production:**
- Generate strong password hashes
- Use environment variables for secrets
- Enable HTTPS
- Consider OAuth/SSO

## 🎓 What You Learned

This setup demonstrates:
- ✅ File-based databases for portability
- ✅ PostgreSQL compatibility for migrations
- ✅ Schema-first development (DDL scripts)
- ✅ Proper entity relationships
- ✅ Password security (BCrypt)
- ✅ Separation of concerns (entities, repositories, services)

## 💡 Tips

1. **Reset often during development** - Just delete the DB file!
2. **Use a SQL client** to inspect data (DBeaver, IntelliJ, etc.)
3. **Check the logs** for SQL queries (`show-sql: true`)
4. **Commit the .data folder to .gitignore** (don't commit DB files!)

---

**Database Status:** ✅ READY  
**Documentation:** ✅ COMPLETE  
**Sample Data:** ✅ LOADED  
**Team Ready:** ✅ YES  

Happy coding! 🚀
