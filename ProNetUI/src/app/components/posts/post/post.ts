import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PostService } from '../../../services/posts/post-service';
import { PostModal } from '../../../model/posts/post-modal';
import { TimeAgoPipe } from '../../../dto/TimeAgoPipe';

@Component({
  selector: 'app-post',
  standalone: true,
  imports: [CommonModule, FormsModule, TimeAgoPipe],
  templateUrl: './post.html',
  styleUrl: './post.css',
})
export class Post implements OnInit{

  posts : PostModal[];

  // ===== Modal State =====
  isModalOpen = false;

  // ===== Composer State =====
  postText = '';
  linkPreviewUrl: string | null = null;

  // ===== Limits / UI =====
  maxChars = 3000;
  isSubmitting = false;

  // ===== TEMP: Replace with real logged-in user id (from Auth service later) =====
  userId = 3;

  constructor(private postService: PostService) {}

  ngOnInit(): void {
  this.getAllPosts();
  }

  getAllPosts(){
    this.postService.getAllPosts().subscribe({
      next: (posts) =>{
        this.posts = posts;
        console.log(this.posts);
      },
      error : (err)=>{
        console.error('Failed to get posts:', err);
      }
    })
  }

  // ===== Modal Controls =====
  openModal(): void {
    this.isModalOpen = true;
  }

  closeModal(): void {
    if (this.isSubmitting) return; // prevent closing while submitting (optional)
    this.isModalOpen = false;
  }

  // ===== Link Preview (mock) =====
  addMockLinkPreview(): void {
    this.linkPreviewUrl = 'https://example.com/some-article';
  }

  removeLinkPreview(): void {
    this.linkPreviewUrl = null;
  }

  // ===== Attach actions (mock) =====
  attachMock(type: 'photo' | 'video' | 'event'): void {
    // For now just show a small hint inside caption (optional)
    const tag = type === 'photo' ? '[Photo]' : type === 'video' ? '[Video]' : '[Event]';
    if (!this.postText.includes(tag)) {
      this.postText = (this.postText + (this.postText ? ' ' : '') + tag).trim();
    }
  }

  // ===== Validation =====
  isPostDisabled(): boolean {
    const trimmed = (this.postText || '').trim();
    return this.isSubmitting || !trimmed || trimmed.length > this.maxChars;
  }

  // ===== Submit =====
  submitPost(): void {
    if (this.isPostDisabled()) return;

    this.isSubmitting = true;

    // Match your backend entity:
    // Post has: caption, visibility, and user is provided by URL (/api/users/{userId}/post)
    // So payload should NOT include user object (backend should attach user from path variable).
    const payload: PostModal = {
      caption: this.postText.trim(),
      // visibility optional - depends on your DTO/backend defaults
      // visibility: 'PUBLIC',
    } as PostModal;

    this.postService.addPost(payload, this.userId).subscribe({
      next: (created) => {
        // Reset composer
        this.postText = '';
        this.linkPreviewUrl = null;

        // Close modal
        this.isModalOpen = false;

        // TODO later: emit event / refresh feed list
        console.log('Post created:', created);
      },
      error: (err) => {
        console.error('Failed to create post:', err);
        // Optional: show UI message/toast later
      },
      complete: () => {
        this.isSubmitting = false;
        this.getAllPosts();
      },
    });
  }
}
