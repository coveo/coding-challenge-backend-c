using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

namespace CitySearch.Controllers
{
    public class SuggestionsController : Controller
    {
        // GET: CitySearch
        public ActionResult Index()
        {
            return View();
        }

        // GET: CitySearch/Details/5
        public ActionResult Details(int id)
        {
            return View();
        }

        // GET: CitySearch/Create
        public ActionResult Create()
        {
            return View();
        }

        // POST: CitySearch/Create
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Create(IFormCollection collection)
        {
            try
            {
                // TODO: Add insert logic here
                return RedirectToAction("Index");
            }
            catch
            {
                return View();
            }
        }

        // GET: CitySearch/Edit/5
        public ActionResult Edit(int id)
        {
            return View();
        }

        // POST: CitySearch/Edit/5
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Edit(int id, IFormCollection collection)
        {
            try
            {
                // TODO: Add update logic here

                return RedirectToAction("Index");
            }
            catch
            {
                return View();
            }
        }
    }
}