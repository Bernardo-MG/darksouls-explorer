import { Relationship } from 'app/models/relationship';

export interface MentionResponse {
    relationships: Array<Relationship>;
}