package org.example.model;

import org.example.model.enumeration.Role;

import java.util.Date;
import java.util.List;

public class PremiumUser extends User{

    Date expirationDate;
    public PremiumUser(long id, Role role, String username, List<Recipe> favoriteRecipes, Inventory inventory, Date expirationDate) {
        super(id, role, username, favoriteRecipes, inventory);
        this.expirationDate = expirationDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void extendMembershipByDays(int days) {
        this.expirationDate = new Date(this.expirationDate.getTime() + (long) days * 24 * 60 * 60 * 1000);
    }

    public boolean isExpired() {
        return this.expirationDate.before(new Date());
    }

    @Override
    public boolean isPremium() {
        return true;
    }

}
