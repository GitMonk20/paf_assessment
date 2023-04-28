package ibf2022.assessment.paf.batch3.controllers;

import ibf2022.assessment.paf.batch3.models.Beer;
import ibf2022.assessment.paf.batch3.models.Brewery;
import ibf2022.assessment.paf.batch3.models.Style;
import ibf2022.assessment.paf.batch3.repositories.BeerRepository;
import ibf2022.assessment.paf.batch3.repositories.OrderRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ibf2022.assessment.paf.batch3.services.BeerService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.Optional;

@Controller
public class BeerController {

    @Autowired
    private BeerService beerService;

    private final BeerRepository beerRepository;
    private final OrderRepository orderRepository;

    public BeerController(BeerRepository beerRepository, OrderRepository orderRepository) {
        this.beerRepository = beerRepository;
        this.orderRepository = orderRepository;
    }

	//TODO Task 2 - view 0
    @GetMapping("/")
    public String view0(Model model) {
        List<Style> styles = beerRepository.getStyles();
        model.addAttribute("styles", styles);
        return "view0";
    }	
	
	//TODO Task 3 - view 1
    @GetMapping("/beer/style/{id}")
    public String view1(@PathVariable("id") int styleId, @RequestParam("styleName") String styleName, Model model) {
        List<Beer> beers = beerRepository.getBreweriesByBeer(styleId);
        model.addAttribute("styleName", styleName);
        model.addAttribute("beers", beers);
        model.addAttribute("hasBeers", !beers.isEmpty());
        return "view1";
    }

	//TODO Task 4 - view 2
    @GetMapping("/brewery/{id}")
    public String view2(@PathVariable("id") int breweryId, Model model) {
        Optional<Brewery> optionalBrewery = beerRepository.getBeersFromBrewery(breweryId);
        if (optionalBrewery.isPresent()) {
            Brewery brewery = optionalBrewery.get();
            model.addAttribute("brewery", brewery);
            model.addAttribute("beers", brewery.getBeers());
            model.addAttribute("hasBeers", !brewery.getBeers().isEmpty());
        } else {
            model.addAttribute("hasBeers", false);
        }
        return "view2";
    }
	
	//TODO Task 5 - view 2, place order
    @PostMapping("/brewery/{breweryId}/order")
    public String placeOrder(@PathVariable int breweryId, HttpServletRequest request, Model model) {
        String[] beerIds = request.getParameterValues("beerId");
        List<Map<String, Integer>> orders = new ArrayList<>();

        if (beerIds != null) {
            for (String beerId : beerIds) {
                if (beerId == "")
                    continue;
                int id = Integer.parseInt(beerId);
                String quantityStr = request.getParameter(beerId);

                if (quantityStr != null && !quantityStr.isEmpty()) {
                    int quantity = Integer.parseInt(quantityStr);

                    if (quantity > 0) {
                        Map<String, Integer> order = new HashMap<>();
                        order.put("beerId", id);
                        order.put("quantity", quantity);
                        orders.add(order);
                    }
                }
            }
        }

        String orderId = beerService.placeOrder(breweryId, orders);
        model.addAttribute("orderId", orderId);
        return "view3";
    }
}
