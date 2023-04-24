export interface User {
    username: string,
    email: string,
    password: string
}

export interface CatListResponse {
    cats: Cat[],
    nextURL: string
}

export interface CatDetailsResponse {
    cat: Cat,
    saved: boolean
}

export interface Cat {
    cat_id: string,
    storedUUID: string,
    label: string,
    image: string,
    link: string,
}

export interface Post {
    post_id: number,
    email: string,
    username: string,
    title: string,
    caption: string,
    cat_id: string,
    cat_label: string,
    likes: number,
    date: string,
    imageUUID: string,
    liked: boolean
    ownPost: boolean
}

export interface SavedCat {
    cat_label: string,
    cat_id: string
}

export interface PostDelete {
    post_id: number,
    email: string
}