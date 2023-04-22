export interface Post {
	email: string
	title: string
	text: string
	image: File
}

export interface PostResponse {
	postId: string
}

export interface User {
    username: string,
    email: string,
    password: string
}