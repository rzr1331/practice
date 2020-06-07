package com.locus;

import com.locus.data.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.locus.data.ActionType.*;
import static com.locus.data.ResourceType.*;
import static com.locus.data.RoleName.*;

public class Application {

    private static Map<RoleName, Role> roleHashMap = new HashMap<>();

    // Creating fake roles
    static {
        /**
         * List of available roles (assumptions) - SALES/OPERATIONS/FINANCE with following attributes :
         * ------------------------------------------------------
         * RoleName : SALES
         * ResourceType : ORDER, ActionType : READ,WRITE
         * ResourceType : DISPATCH, ActionType : READ
         * ResourceType : SHIPMENT, ActionType : READ
         * ------------------------------------------------------
         * RoleName : OPERATIONS
         * ResourceType : SHIPMENT, ActionType : READ,WRITE,DELETE
         * ResourceType : DISPATCH, ActionType : READ,WRITE
         * ------------------------------------------------------
         * RoleName : FINANCE
         * ResourceType : ORDER, ActionType : READ
         * ------------------------------------------------------
         */
        Set<Resource> salesResources = new HashSet<>();
        salesResources.add(Resource.Builder.resource()
                .withResourceType(ORDER)
                .withActionType(Stream.of(READ,WRITE).collect(Collectors.toSet()))
                .build());
        salesResources.add(Resource.Builder.resource()
                .withResourceType(DISPATCH)
                .withActionType(Stream.of(READ).collect(Collectors.toSet()))
                .build());
        salesResources.add(Resource.Builder.resource()
                .withResourceType(SHIPMENT)
                .withActionType(Stream.of(READ).collect(Collectors.toSet()))
                .build());

        Set<Resource> opsResources = new HashSet<>();
        opsResources.add(Resource.Builder.resource()
                .withResourceType(SHIPMENT)
                .withActionType(Stream.of(READ,WRITE,DELETE).collect(Collectors.toSet()))
                .build());
        opsResources.add(Resource.Builder.resource()
                .withResourceType(DISPATCH)
                .withActionType(Stream.of(READ,WRITE).collect(Collectors.toSet()))
                .build());

        Set<Resource> financeResources = new HashSet<>();
        financeResources.add(Resource.Builder.resource()
                .withResourceType(ORDER)
                .withActionType(Stream.of(READ).collect(Collectors.toSet()))
                .build());

        Role salesRole = Role.Builder.role()
                .withRoleName(SALES)
                .withResources(salesResources)
                .build();

        Role opsRole = Role.Builder.role()
                .withRoleName(OPERATIONS)
                .withResources(opsResources)
                .build();

        Role financeRole = Role.Builder.role()
                .withRoleName(FINANCE)
                .withResources(financeResources)
                .build();

        roleHashMap.put(SALES, salesRole);
        roleHashMap.put(OPERATIONS, opsRole);
        roleHashMap.put(FINANCE, financeRole);
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("######################################################");
        System.out.println("##################--RBAC Simulator--##################");
        System.out.println("######################################################");

        System.out.println("List of available roles (assumptions) - SALES/OPERATIONS/FINANCE with following attributes :");
        System.out.println("------------------------------------------------------");
        System.out.println("RoleName : SALES");
        System.out.println("ResourceType : ORDER, ActionType : READ,WRITE");
        System.out.println("ResourceType : DISPATCH, ActionType : READ");
        System.out.println("ResourceType : SHIPMENT, ActionType : READ");
        System.out.println("------------------------------------------------------");
        System.out.println("RoleName : OPERATIONS");
        System.out.println("ResourceType : SHIPMENT, ActionType : READ,WRITE,DELETE");
        System.out.println("ResourceType : DISPATCH, ActionType : READ,WRITE");
        System.out.println("------------------------------------------------------");
        System.out.println("RoleName : FINANCE");
        System.out.println("ResourceType : ORDER, ActionType : READ");
        System.out.println("------------------------------------------------------");

        System.out.println("");
        System.out.println("");
        System.out.println("");

        System.out.println("######################################################");
        System.out.println("Creating a dummy user with name AVICHAL for simulation purpose.");
        System.out.println("######################################################");

        User user = User.Builder.user()
                .withName("AVICHAL")
                .withRoles(new HashSet<>())
                .build();

        System.out.println("");
        System.out.println("");
        System.out.println("");


        int exit = 0;
        while (exit == 0) {
            System.out.println("######################################################");
            System.out.println("What do you wish to do ?");
            System.out.println("1. Get all roles assigned to dummy user.");
            System.out.println("2. Add a role to dummy user.");
            System.out.println("3. Remove a role from dummy user.");
            System.out.println("4. Check resource access for dummy user.");
            System.out.println("5. Exit");
            System.out.println("Please enter the operation you wish to perform by entering the serial no.");
            String input = scanner.nextLine();

            switch (input) {
                case "1" : {
                    System.out.println("RESULT ========> List of roles assigned to dummy user : " +
                            user.getRoles().stream().map(Role::getRoleName).collect(Collectors.toList()));
                    break;
                }
                case "2" : {
                    System.out.println("Please enter role name to add - (SALES/OPERATIONS/FINANCE) : ");
                    String roleName = scanner.nextLine();
                    addRoleToUser(user, roleName);
                    break;
                }
                case "3" : {
                    System.out.println("Please enter role name to remove - (SALES/OPERATIONS/FINANCE) : ");
                    String roleName = scanner.nextLine();
                    removeRoleFromUser(user, roleName);
                    break;
                }
                case "4" : {
                    System.out.println("Available ResourceType-(ORDER/DISPATCH/SHIPMENT), Available ActionType (READ/WRITE/DELETE)");
                    System.out.println(" Please enter in this format => ResourceType:ActionType (eg. SHIPMENT:READ)");
                    String resourceActionString = scanner.nextLine();
                    checkResourceActionAccess(user, resourceActionString);
                    break;
                }
                case "5" : {
                    exit = 1;
                    break;
                }
            }
            System.out.println("######################################################");
            System.out.println("");
            System.out.println("");
            System.out.println("");
        }
    }

    private static void addRoleToUser (User user, String roleNameString) {
        RoleName roleName = RoleName.getRoleName(roleNameString);
        if (roleName == null) {
            System.out.println("RESULT ========> Wrong role name entered.");
            return;
        }
        Set<Role> existingRoles = user.getRoles();

        if (existingRoles.stream().filter(role -> roleName.equals(role.getRoleName())).findFirst().orElse(null) != null) {
            System.out.println("RESULT ========> Role already present for user.");
            return;
        }

        existingRoles.add(roleHashMap.get(roleName));
        System.out.println("RESULT ========> Role added successfully.");
    }

    private static void removeRoleFromUser (User user, String roleNameString) {
        RoleName roleName = RoleName.getRoleName(roleNameString);
        if (roleName == null) {
            System.out.println("RESULT ========> Wrong role name entered.");
            return;
        }

        Set<Role> existingRoles = user.getRoles();

        if (existingRoles.stream().filter(role -> roleName.equals(role.getRoleName())).findFirst().orElse(null) == null) {
            System.out.println("RESULT ========> Role not present for user. Nothing to do.");
            return;
        }

        existingRoles.remove(roleHashMap.get(roleName));
        System.out.println("RESULT ========> Role removed successfully.");
    }

    private static boolean checkResourceActionAccess(User user, String resourceActionString) {
        String[] resourceActionArray = resourceActionString.split(":");
        if (resourceActionArray.length != 2) {
            System.out.println("RESULT ========> Malformed ResourceType:ActionType String.");
            return false;
        }

        ResourceType inputResourceType = ResourceType.getResourceTypeByName(resourceActionArray[0]);
        ActionType inputActionType = ActionType.getActionTypeByName(resourceActionArray[1]);
        if (inputResourceType == null || inputActionType == null) {
            System.out.println("RESULT ========> Malformed ResourceType:ActionType String.");
            return false;
        }

        for (Role role : user.getRoles()) {
            for (Resource resource : role.getResources()) {
                if (!inputResourceType.equals(resource.getResourceType())) {
                    continue;
                }
                for (ActionType actionType : resource.getActionType()) {
                    if (inputActionType.equals(actionType)) {
                        System.out.println("RESULT ========> User has access to : "
                                + inputResourceType + ":" + inputActionType);
                        return true;
                    }

                }
            }
        }

        System.out.println("RESULT ========> User does not have access to : "
                + inputResourceType + ":" + inputActionType);
        return false;
    }

}
