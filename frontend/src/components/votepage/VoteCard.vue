<template>
  <div>
    <div>
      <b-card
        img-src="https://picsum.photos/600/300/?image=25"
        img-alt="Image"
        img-top
        tag="article"
        style="max-width: 15rem;"
        class="mb-2"
      >
        <b-card-title>{{ title }}</b-card-title>
        <b-card-text>
          Some quick example text to build on the card title and make up the
          bulk of the card's content.
        </b-card-text>
      </b-card>

      <b-form-checkbox
        id="checkbox-1"
        v-model="status"
        name="checkbox-1"
        value="accepted"
        unchecked-value="not_accepted"
      >
        I accept the terms and use
      </b-form-checkbox>
    </div>
  </div>
</template>

<script>
export default {
  name: 'VoteCard',
  props: ['title'],
};
</script>
