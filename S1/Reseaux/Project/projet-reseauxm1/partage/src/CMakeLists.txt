add_library(iftun STATIC iftun.h iftun.c)

add_executable(test_iftun test_iftun.c)
target_link_libraries(test_iftun iftun)

add_library(extremite STATIC extremite.h extremite.c)
target_link_libraries(extremite iftun)

add_executable(test_ext_in test_ext_in.c)
target_link_libraries(test_ext_in iftun extremite)

add_executable(test_ext_out test_ext_out.c)
target_link_libraries(test_ext_out iftun extremite)

add_executable(el-tunel el-tunel.c)
target_link_libraries(el-tunel iftun extremite)
add_custom_command(TARGET el-tunel POST_BUILD
                   COMMAND ${CMAKE_COMMAND} -E copy
                   $<TARGET_FILE:el-tunel> ${CMAKE_SOURCE_DIR}/partage
)
