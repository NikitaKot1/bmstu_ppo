package bl.repository

import bl.entities.*
import java.time.LocalDateTime

var ManufacturerMockData: Array<Manufacturer> = arrayOf(
    Manufacturer(0u, "login0", "password0"),
    Manufacturer(1u, "login1", "password1"),
    Manufacturer(2u, "login2", "password2"),
    Manufacturer(3u, "login3", "password3"),
    Manufacturer(4u, "login4", "password4")
)

var ConsumerMockData: Array<Consumer> = arrayOf(
    Consumer(0u, "login0", "password0"),
    Consumer(1u, "login1", "password1"),
    Consumer(2u, "login2", "password2"),
    Consumer(3u, "login3", "password3"),
    Consumer(4u, "login4", "password4")
)

var CommentMockData: Array<Comment> = arrayOf(
    Comment(0u, LocalDateTime.now(), "text0", ConsumerMockData[0]),
    Comment(1u, LocalDateTime.now(), "text1", ConsumerMockData[1]),
    Comment(2u, LocalDateTime.now(), "text2", ConsumerMockData[2]),
    Comment(3u, LocalDateTime.now(), "text3", ConsumerMockData[3]),
    Comment(4u, LocalDateTime.now(), "text4", ConsumerMockData[4])
)

var IngredientMockData: Array<Ingredient> = arrayOf(
    Ingredient(0u, "name0", IngredientType.HOP, 987u),
    Ingredient(1u, "name1", IngredientType.MALT, 987u),
    Ingredient(2u, "name2", IngredientType.MALT, 987u),
    Ingredient(3u, "name3", IngredientType.HOP, 987u),
    Ingredient(4u, "name4", IngredientType.YEAST, 987u)
)

var RecipeMockData: Array<Recipe> = arrayOf(
    Recipe(
        0u,
        "name0",
        "description0",
        LocalDateTime.now(),
        CommentMockData.toList(),
        ManufacturerMockData[0],
        IngredientMockData.toList()
    ),
    Recipe(
        1u,
        "name1",
        "description1",
        LocalDateTime.now(),
        CommentMockData.toList(),
        ManufacturerMockData[1],
        IngredientMockData.toList()
    ),
    Recipe(
        2u,
        "name2",
        "description2",
        LocalDateTime.now(),
        CommentMockData.toList(),
        ManufacturerMockData[2],
        IngredientMockData.toList()
    ),
    Recipe(
        3u,
        "name3",
        "description3",
        LocalDateTime.now(),
        CommentMockData.toList(),
        ManufacturerMockData[3],
        IngredientMockData.toList()
    ),
    Recipe(
        4u,
        "name4",
        "description4",
        LocalDateTime.now(),
        CommentMockData.toList(),
        ManufacturerMockData[4],
        IngredientMockData.toList()
    ),
    Recipe(
        8u,
        "name8",
        "description8",
        LocalDateTime.now(),
        CommentMockData.toList(),
        ManufacturerMockData[4],
        IngredientMockData.toList()
    )
)

var SavedRecipesMockData: Array<Pair<ULong, ULong>> = arrayOf(
    Pair(0u, 0u),
    Pair(0u, 1u),
    Pair(0u, 2u),
    Pair(2u, 3u),
    Pair(4u, 1u)
)

var PublishQueue: Array<ULong> = arrayOf(1u, 8u)