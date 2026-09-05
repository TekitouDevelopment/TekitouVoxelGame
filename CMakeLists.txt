cmake_minimum_required(VERSION 4.4.3)
project(TekitouVoxelGame)

set(CMAKE_CXX_STANDARD 26)
set(CMAKE_CXX_STANDARD_REQUIRED ON)


include(FetchContent)
FetchContent_Declare(
        SDL3
        GIT_REPOSITORY https://github.com/libsdl-org/SDL.git
        GIT_TAG        release-3.4.16
)
FetchContent_MakeAvailable(SDL3)

add_executable(TekitouVoxelGame_Client
        src/Client/entry.cpp
        src/Client/main.hpp
        src/Client/main.cpp
        src/Client/SDLMgr/sdlmgr.hpp
        src/Client/SDLMgr/sdlmgr.cpp
        src/Client/Render/render.cpp
        src/Client/Render/render.hpp
        src/Client/Render/Vulkan/vulkan.hpp
        src/Client/Render/Vulkan/vulkan.cpp
)

target_link_libraries(TekitouVoxelGame_Client PRIVATE SDL3::SDL3)